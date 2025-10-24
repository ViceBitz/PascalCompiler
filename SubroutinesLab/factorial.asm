# Calculates and returns the factorial of a number (n!) = n*(n-1)*(n-2)...
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
sw $v0 n

#Set parameter (n) and call fact(n)
lw $a0 n
jal fact

#Print the result
move $a0 $v0
li $v0 1
syscall

li $v0 10
syscall

fact:
#Push $ra and parameters ($a0 = n) onto stack
subu $sp $sp 8
sw $ra ($sp)
sw $a0 4($sp)

#If a0 == 0 then branch to base (default return value is 1)
li $v0 1
beq $a0, 0, factBase

#Else, run fact(a0-1)
subu $a0 $a0 1
jal fact

#Do fact(n) = fact(n-1) * n (or $v0 * $a0)
lw $a0 4($sp) #Load the current argument (in stack, offset to throw away 0)
mul $v0, $v0, $a0


factBase:
#Pop $ra and parameter $a0 from stack
lw $ra ($sp)
lw $a0 4($sp)
addu $sp $sp 8
jr $ra #Return up the recursive chain

.data
prompt1: .asciiz "Number: "
n: .word 0
