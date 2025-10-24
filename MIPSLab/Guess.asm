#A simple guessing game where the computer picks a number from
#1 to 100 and the player continuously attempts to guess the
#number. If their guess is high or low, an accompanying prompt
#informs them. If their guess is correct, the program prompts
#correct and stops.
#
#
#
# @author Victor Gong
# @version 11/7/2023
#

.text 0x00400000
.globl main

main:

#Choose random number
li $a1, 100
li $v0, 42
syscall
add $a0,$a0,1
move $t0,$a0

j whileLoop

whileLoop:

#Prompt guess
li $v0, 4
la $a0, msg
syscall

#Read input
li $v0, 5
syscall
move $t1,$v0

#If $t1 (guess) > $t0 (number), prompt "HIGH"
bgt $t1,$t0,promptHigh

#If $t1 (guess) < $t0 (number), prompt "LOW"
blt $t1,$t0,promptLow

#If equal, prompt guess right (at label 'end')
beq $t1,$t0,end

promptHigh:
#Prompt high message
li $v0, 4
la $a0, msgH
syscall
#Jump back to while loop
j whileLoop

promptLow:
#Prompt low message
li $v0, 4
la $a0, msgL
syscall
#Jump back to while loop
j whileLoop

end:
#Print correct message
li $v0, 4
la $a0, msgW
syscall
#Quit
li $v0, 10
syscall

.data
msgH:
.asciiz "High!\n"
msgL:
.asciiz "Low!\n"
msgW:
.asciiz "Correct!"
msg:
.asciiz "Guess a number between 1 and 100: "
nl:
.asciiz "\n"
