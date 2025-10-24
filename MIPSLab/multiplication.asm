#Takes in two values from the user and prints out the product
#of the two numbers
#
#
#
# @author Victor Gong
# @version 11/7/2023
#


.text 0x00400000
.globl main

main:

#Prompt first number
li $v0, 4
la $a0, msgF
syscall

#Read input
li $v0, 5
syscall
move $t0,$v0

#Prompt second number
li $v0, 4
la $a0, msgS
syscall

#Read input
li $v0, 5
syscall
move $t1,$v0

#Print the result of the multiplication
li $v0, 1
mult $t0, $t1
mflo $a0
syscall

li $v0, 10
syscall

.data
msgF:
.asciiz "First number: "
msgS:
.asciiz "Second number: "

