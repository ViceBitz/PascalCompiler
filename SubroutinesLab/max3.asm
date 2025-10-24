# Calculates and returns the maximum of three numbers (a,b,c) in a subroutine called max3(a,b,c)
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

#Prompt3
li $v0 4
la $a0 prompt3
syscall

#Input3
li $v0 5
syscall
sw $v0 c

#Set parameters (a,b,c) and call max3(a,b,c)
lw $a0 a
lw $a1 b
lw $a2 c
jal max3

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

max3:
#Push $ra on stack
subu $sp $sp 4
sw $ra ($sp)

jal max2 #Call max2 ($a0 and $a1 already set in main)
move $a0 $v0 #Save max2($a0, $a1) to $a0
move $a1 $a2 #Set $a1 = $a2
jal max2 #Call max2 again with $a0 as max2($a0, $a1), $a1 as $a2

lw $ra ($sp) #Pop $ra from stack
addu $sp $sp 4
jr $ra #Return to caller

.data
prompt1: .asciiz "Number 1: "
prompt2: .asciiz "Number 2: "
prompt3: .asciiz "Number 3: "
a: .word 8
b: .word 7
c: .word 100
