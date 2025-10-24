#Takes in an integer from the user and prints odd if the
#number is odd or even if the number is even
#
#
#
# @author Victor Gong
# @version 11/7/2023
#

.text 0x00400000
.globl main

main:

#Prompt
li $v0, 4
la $a0, msg
syscall

#Read input
li $v0, 5
syscall
move $t0,$v0

#Calculate modulo 2
rem $t1, $t0, 2

#If 0, jump to even
beq $t1, 0, even
#Else odd
li $v0, 4
la $a0, oddMsg
syscall

#Quit
j end

even:
li $v0, 4
la $a0, evenMsg
syscall

end:
li $v0, 10
syscall



.data
msg: .asciiz "Number: "
evenMsg: .asciiz "Even"
oddMsg: .asciiz "Odd"


