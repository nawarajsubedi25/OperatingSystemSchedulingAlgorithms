## A Java program that simulates the CPU scheduling algorithms (1) First Come First Served (fcfs), (2) Shortest Job Next Non-Preemptive (sjnnp), and (3) Priority Non-Preemptive (pnp).Your program will read an input file, in.txt, which will contain data about theCPU requests. 

Program will open (not a command line argument) and read/process the input file, in.txt, line by line
* The first line in the input file contains two tokens. The first token is a single
character; either an ‘a’ or ‘A’, which specifies that the algorithm your
program is to simulate. The second token on this line will defines the actual
algorithm; fcfs, sjnnp, or pnp
* All other lines in the input file will begin with either ‘p’or ‘P’, which
specifies a CPU request by a process. Three additional tokens will follow on
these lines for the fcfs and sjnnp algorithms. The first token defines the
process id number (pid) of the process requesting the CPU. The second token defines the time stamp at which the CPU is requested by the process. The
third, and last, token defines the CPU burst duration of the process.
* The pnp algorithm will define four tokens following a ‘p’or ‘P’. The first
three tokens are the same as defined for the fcfs and sjnnp algorithms.
The fourth token defines the priority of the process.

If the CPU scheduling algorithm specified in the input file is fcfs, then your
program must process the input file, in.txt, using the First Come First Served CPU
scheduling algorithm. If the CPU scheduling algorithm specified in the input file,
in.txt, is sjnnp, then your program must process the input file using the Shortest
Job Next Non-Preemptive CPU scheduling algorithm. If the CPU scheduling
algorithm specified in the input file, in.txt, is pnp, then your program must
process the input file using the Priority Non-Preemptive CPU scheduling algorithm.

You may assume the following:
* All process ids will be positive integers
* All time stamps will be positive integers
* All CPU bursts will be positive integers
* All priorities will be positive integers
* If no CPU requests exist at a certain clock interval, but there still exist
pending CPU requests for later clock intervals, then your program should print
the clock interval and move to the next clock interval
* All output generated by your program should be directed to either the output
file fcfs_out.txt, sjnnp_out.txt, or pnp_out.txt, depending
on the algorithm specified by the ‘a’ or ‘A’ line in the input file.
