# Handles the creation of linked list, saving it in memory with null as the address of 0
#
# @author Victor Gong
# @version 12/6/2023

.globl main
.text

main:

#Make a linked list of null<--5<--7<--4<--3
li $a0 5
move $a1 $0
jal makeNewListNode

li $a0 7
move $a1 $v0
jal makeNewListNode

li $a0 4
move $a1 $v0
jal makeNewListNode

li $a0 3
move $a1 $v0
jal makeNewListNode

move $a0 $v0 #Set the parameter $a0
jal sumLinkedList

#Print value
move $a0 $v0
li $v0 1
syscall

li $v0 10
syscall

#Makes a new ListNode with a specific value and next ListNode, in the form makeNewListNode($a0=value, $a1=address of next ListNode)
makeNewListNode:
move $t0 $a0 #Save $a0 parameter in a temp register (need $a0 = 8 for allocating memory)

#Allocate 8 bytes in memory (address of first byte saved in $v0)
li $v0 9
li $a0 8
syscall

#Save value to first four bytes
sw $t0 ($v0)
#Save address of next to the last four bytes
sw $a1 4($v0)

#Return to caller
jr $ra

#Sums the LinkedList that starts at the address of the head ($a0), with recursion
sumLinkedList:
#Push $ra and parameter($a0 = currentNode) to the stack
subu $sp $sp 8
sw $ra ($sp)
sw $a0 4($sp)

#Base case, $a0 is $0, return 0
li $v0 0
beqz $a0 sumLinkedListReturn

#Else, do sumLinkedList = sumLinkedList($a0.next) + value (next at 4($a0), value at ($a0))

#Push the value of the node into the stack
subu $sp $sp 4
lw $t0 ($a0)
sw $t0 ($sp)

lw $a0 4($a0)
jal sumLinkedList #Call sumLinkedList($a0.next)

#Pop the value of the node from the stack
lw $t0 ($sp)
addu $sp $sp 4

addu $v0 $v0 $t0 #Add value to the result of the call

sumLinkedListReturn:
#Pop $ra and parameter $a0 from the stack
lw $ra ($sp)
lw $a0 4($sp)
addu $sp $sp 8

#Return up the recursive chain
jr $ra

.data

