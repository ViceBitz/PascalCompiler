#Simulates a simple for loop that loops from a lower bound
#to an upper bound with a given step and prints all of the
#intermediate values along the way
#
#
#
# @author Victor Gong
# @version 11/7/2023
#


.text 0x00400000
.globl main

main:

#Prompt lower bound
li $v0, 4
la $a0, msgF
syscall

#Read input
li $v0, 5
syscall
move $t0,$v0

#Prompt upper bound
li $v0, 4
la $a0, msgS
syscall

#Read input
li $v0, 5
syscall
move $t1,$v0

#Prompt step
li $v0, 4
la $a0, msgT
syscall

#Read input
li $v0, 5
syscall
move $t2,$v0

#Loop and print the values one by one (using ($t3 as loop variable)
move $t3,$t0

j forLoop

forLoop:

bgt $t3,$t1,end #Branch to end if loopVar >= upper bound

#Print the current loop variable
li $v0, 1
move $a0, $t3
syscall
#Print newline
li $v0, 4
la $a0,nl
syscall

#Add step to the loop variable
add $t3,$t3,$t2

j forLoop

end:
li $v0, 10
syscall

.data
msgF:
.asciiz "Lower bound: "
msgS:
.asciiz "Upper bound: "
msgT:
.asciiz "Step: "
nl:
.asciiz "\n"
