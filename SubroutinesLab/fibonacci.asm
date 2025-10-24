# Calculates the nth fibonacci sequence (1,1,2,3,5,8..) using a recursive subroutine
#
# @author Victor Gong
# @version 12/8/2023

.text
.globl main

#fib($a0) = $v0, ($sp) -> $ra, 4($sp) -> $a0, 8($sp) -> $a1
main:
#Prompt1
li $v0 4
la $a0 prompt1
syscall

#Input1
li $v0 5
syscall
sw $v0 n

#Set parameter (n) and call fib(n)
lw $a0 n
jal fib

#Print result
move $a0 $v0
li $v0 1
syscall

li $v0 10
syscall

fib:
#Push $ra and parameters ($a0 = n) onto stack
subu $sp $sp 8
sw $ra ($sp)
sw $a0 4($sp)

#Base case, $a0 = 0, $v0 = 0; $a0 = 1, $v0 = 1
li $v0 0 #returns 0
beq $a0 0 fibBase
li $v0 1 #returns 1
beq $a0 1 fibBase

#Else fib($a0) = fib($a0 - 1) + fib($a0 - 2)

subu $a0 $a0 1 #a0 -= 1
jal fib
subu $sp $sp 4
sw $v0 ($sp) #Push result on stack


subu $a0 $a0 1 #a0 -= 1
jal fib
lw $t0 ($sp) #Pop fib($a0-1) result from stack
addu $sp $sp 4

addu $v0 $t0 $v0 #Add fib($a0-1) with fib($a0-2)

fibBase:
lw $ra ($sp) #Pop $ra and parameter $a0 from stack
lw $a0 4($sp)
addu $sp $sp 8
jr $ra #Return up the recursive chain

.data
prompt1: .asciiz "Number: "
n: .word 0

