# Calculates and returns the maximum of two numbers (a,b) in a subroutine called max2(a,b)
#
# @author Victor Gong
# @version 12/6/2023

.globl main
.text

main:
#Prompt1
li $v0 4
la $a0 prompt1
syscall

#Input1
li $v0 5
syscall
sw $v0 a

#Prompt2
li $v0 4
la $a0 prompt2
syscall

#Input2
li $v0 5
syscall
sw $v0 b

#Set the parameters to $a0, $a1 and call max2
lw $a0 a
lw $a1 b
jal max2

#Print the result
move $a0 $v0
li $v0 1
syscall

li $v0 10
syscall

max2:
#Push $ra on stack
subu $sp $sp 4
sw $ra ($sp)

move $v0 $a1 #Assume max value is $a1
bgt $v0 $a0 returnMax2 #If $a1 is the correct value ($v0 > $a0), skip rest of code and go to return
move $v0 $a0 #Set $a0 as the new maximum

returnMax2:
lw $ra ($sp) #Pop $ra from stack
addu $sp $sp 4
jr $ra #Return to caller

.data
prompt1: .asciiz "Number 1: "
prompt2: .asciiz "Number 2: "
a: .word 8
b: .word 7
