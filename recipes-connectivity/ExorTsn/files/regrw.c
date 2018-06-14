
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <assert.h>


/*
  root@exoralterakit:~# strings  /media/mmcblk1p1/socfpga.dtb  | grep 0x
  fpgamgr@0xff706000 -- ID read 0 0
  sdrctl@0xffc25000 --  ID read BUS error
  l3regs@0xff800000 -- ID read crash tutto
  ulti_sdc@0xFF230000 -- ID read 0 0
  esempio exor, HPS-to-FPGA bridge, start a 0xff200000 -- ID read 0x29 0
  driver flexibilis, 0xff300000 -- ID read 0 0
 */

#define HPS2FPGA_AXI_SLAVES_BASE   0xC0000000

//#define AVALON_BASE        0xFF200000 // 0xff300000

#define IPCORE_SWITCH_BASE_ADDR   0x00200000

/*
  Section 4, Table 10
  Customized by EXOR.
 */
#define PORT0_BASE_OFFSET         0x00010000
#define PORT1_BASE_OFFSET         0x00020000
#define PORT2_BASE_OFFSET         0x00030000
#define PORT0_AVALON_BASE_OFFSET  0x00070000
#define PORT1_AVALON_BASE_OFFSET  0x00070200
#define PORT2_AVALON_BASE_OFFSET  0x00070400
#define SWITCH_BASE_OFFSET        0x00000000

#define FRTC_BASE_OFFSET   0x00080000
#define SCHED_BASE_OFFSET  0x000A0000

#define GEN_PORT_STATE     0x00000000

/* Section 4.1, Table 11-12-13-14 */
#define SWITCH_REG_ID0     0x0000
#define SWITCH_REG_ID1     0x0002

uint16_t read_reg16(unsigned char *addr)
{
    return *((uint16_t*)addr);
}

void write_reg16(unsigned char *addr, uint16_t value)
{
    *((uint16_t*)addr) = value;
}

void abortc(char* argv0)
{
	char *usage = "%s -S[s|v|t]|-0|-1|-2 -r|-w addr [value]\n\
addr e value in hex!\n\
type = s|0|1|2\n\
subtype = s|t|v per switch\n";
	fprintf(stderr, usage, argv0);
	exit(1);
}

int main(int argc, char *argv[])
{
    (void)argc;
    (void)argv;
	
    int fd;
	int opt;
	
	int rwflag=-1;
	
	unsigned char *va_reg_base;
    unsigned long reg_offset, reg_base_offset;
    char regtype, action;
    int value;

	while ((opt=getopt(argc, argv,"012r:w:S:"))!=-1)
	{
		switch (opt)
		{
			case 'S':
			{
				char *svalue = optarg;
				reg_base_offset = SWITCH_BASE_OFFSET;
				if (svalue == NULL)
				{
					fprintf(stderr, "port missing");
					abortc(argv[0]);
				}
				switch(svalue[0])
				{
					case 's':
						reg_base_offset += 0;
						break;
					case 't':
						reg_base_offset += 0x2000;
						break;
					case 'v':
						reg_base_offset += 0x4000;

						break;
					default:
						fprintf(stderr, "not existing port");
						abortc(argv[0]);
				}
				break;
			}
			case '0':
			{
				reg_base_offset = PORT0_BASE_OFFSET;
				break;
			}
			case '1':
			{
				reg_base_offset = PORT1_BASE_OFFSET;
				break;
			}
			case '2':
			{
				reg_base_offset = PORT2_BASE_OFFSET;
				break;
			}
			case 'r':
			{
				if (optarg == NULL)
				{
					fprintf(stderr, "address to read not specified");
					abortc(argv[0]);
				}
				rwflag = 0;
				sscanf(optarg, "%lx", &reg_offset);
				
				switch (reg_base_offset)		//vlan selected
				{
					case 0x4000:
					{
						if (reg_offset > 0x1ffe || (reg_offset%0x02) != 0)
						{
							fprintf(stderr, "VLAN configuration register are 16bits and must be between 0 and 0x1ffe");
							exit(1);
						}
						break;
					}
					case 0:
					case 0x2000:
					{
						break;
					}
				}
				break;
			}
			case 'w':
			{
				if (optarg == NULL)
				{
					fprintf(stderr, "address to write not specified");
					abortc(argv[0]);
				}
				rwflag = 1;
				sscanf(optarg, "%lx", &reg_offset);
				if (optind == argc)
				{
					fprintf(stderr, "value parameter missing");
					abortc(argv[0]);
				}
				sscanf(argv[argc-1], "%lx", &value);
				break;
			}
			default:
			{
				fprintf(stderr,"wrong parameter");
				abortc(argv[0]);
			}
		}
	}
    
    fd = open("/dev/mem", O_RDWR|O_SYNC);
    if (fd < 0)
    {
		perror("open");
		exit(EXIT_FAILURE);
    }

    const off_t phy_addr_base = HPS2FPGA_AXI_SLAVES_BASE + IPCORE_SWITCH_BASE_ADDR;
    unsigned long pa_reg_base = phy_addr_base + reg_base_offset;
    unsigned long mmap_size = sysconf(_SC_PAGESIZE);
    while (reg_offset >= mmap_size)
    {
		/* printf("Errore aumentare dimensione mmap (ora %lu), reg %08lx\n", */
		/*        mmap_size, reg_offset); */
		/* exit(1); */
		reg_offset -= mmap_size;
		pa_reg_base += mmap_size;
    }

    va_reg_base = mmap(NULL, mmap_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd,
		   pa_reg_base);
    if (va_reg_base == MAP_FAILED)
    {
    	close(fd);
    	perror("mmap");
    	exit(EXIT_FAILURE);
    }

    if ((reg_offset % 2) != 0)
    {
		printf("Errore registro deve essere allineato a 16 bit!\n");
		exit(1);
    }
        
    switch (rwflag)
    {
		case 0:	//read
		{
			uint16_t value = read_reg16(va_reg_base + reg_offset);
			printf("READ reg %08lx value %04x\n", pa_reg_base+reg_offset, value);
			break;
		}
		case 1:	//write
		{
			printf("WRITE reg %08lx value %04x\n", pa_reg_base+reg_offset, value);
			write_reg16(va_reg_base + reg_offset, value);
			break;
		}
		default:
			fprintf(stderr,"either r or w flag is mandatory");
			exit (1);
    }    

    close(fd);
    
    return 0;
}
