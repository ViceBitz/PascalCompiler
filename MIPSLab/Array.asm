#Asks for 10 numbers from the user, sums up the numbers, calculates
#the average, and keeps track of the min and max, all while the
#numbers are stored in an array
#
#
#
# @author Victor Gong
# @version 11/7/2023
#

.text 0x00400000
.globl main

main:

li $t0, 0 #LB for loop
li $t1, 9 #UB for loop
li $t2, 1 #Step for loop
li $t3, 0 #Loop variable
li $t4, 0 #Input variable
li $t5, 0 #Sum
li $t6, 0 #Average (originally, but moved to $f12)
li $t7, 0 #Max
li $t8, 0 #Min

la $s0, arr

#Loop through 1 to 10 (using ($t3 as loop variable)
move $t3,$t0

j getNumbers

getNumbers:

bgt $t3,$t1,calculateAvg #Branch to average if loopVar >= upper bound

#Prompt
li $v0, 4
la $a0, msg
syscall

#Read number
li $v0, 5
syscall
move $t4, $v0

#Store in the array at position $t3 (loop variable)
sw $t4, ($s0)
#Also add to sum
add $t5, $t5, $t4


#Add step to the loop variable
add $t3,$t3,$t2
#Add 4 to s0
add $s0, $s0, 4

#Set min and max to first input
beq $t3,1,initMinMax
#Check if max < current number
blt $t7,$t4,setMax
#Check if min > current number
bgt $t8,$t4,setMin

j getNumbers

setMax:
move $t7,$t4
j getNumbers

setMin:
move $t8,$t4
j getNumbers

initMinMax:
move $t7, $t4
move $t8, $t4
j getNumbers

calculateAvg:
#Convert $t5 (sum) to a decimal
move $a1, $t5
mtc1.d $a1, $f12
cvt.d.w $f12, $f12

#Get average through sum/10
ldc1 $f2, tenDecimal
div.d $f12, $f12, $f2
j end


end:
#Print everything
#Sum
li $v0, 4
la $a0, msgSum
syscall

li $v0, 1
move $a0, $t5
syscall
#Average
li $v0, 4
la $a0, msgAvg
syscall

li $v0, 3
syscall
#Max
li $v0, 4
la $a0, msgMax
syscall

li $v0, 1
move $a0, $t7
syscall
#Min
li $v0, 4
la $a0, msgMin
syscall

li $v0, 1
move $a0, $t8
syscall

li $v0, 10
syscall

.data
arr: .space 40 #40 bytes = 10 integers
msg: .asciiz "Number: "
msgSum: .asciiz "\nSum: "
msgAvg: .asciiz "\nAverage: "
msgMax: .asciiz "\nMax: "
msgMin: .asciiz "\nMin: "
tenDecimal: .double 10.0



