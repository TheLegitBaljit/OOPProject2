In this part of the project we created a generic task and a custom thread pool. The custom thread pool executes tasks by priority,

and for that we used a priority blocking queue data structure. We chose this type of structure because we wanted to sort the tasks by

their priority, In addition we used a blocking queue becuase it needs to block a thread if the queue is empty or full.

This can be useful when you want to synchronize the access to the queue between multiple threads.


In this project we used a design pattern which is called Factory Method Design Pattern, which belongs to the group of Creational Patterns.

We used it in the Task class so it will be more understandable to create a Task object, without revealing the creational logic behind it.

In addition, we used Adapter design pattern so we can sort the Task objects which are type Callable in the priority queue which is type Runnable.

Here are the main difficulties we encountered while working on the project:

1. We were not sure how the sort the tasks in the thread pool by their priority. we solved it by using the Adapter: OtherFuture,

we implemented the compareTo function in this class and then as soon as new task is added to a thread pool, it will be incremented in the array

in the right position we also decremented the right index when the task is completed, because the max is now updated.

2. Were not sure how to get the max priority in the queue without accessing the queue itself. We solved it by creating a new array 

in the class called "maxPriority" , and each time a task is submitted to the queue, we increment the right cell.


The use of custom thread pool contributed to the performance flexibility, and maintainbility, by executing the higher priority tasks first.

The queue sorts the tasks automatically by their given priority. In addition, we get the maxPriority in O(1) complexity of time and space.

Moreover, the user does not specify the type of the task since the task class is generic, also creating it with lambda expressions.
![UML](https://user-images.githubusercontent.com/85311237/211878991-2867d6e3-ccf0-4432-9bee-7dba05a073fb.png)

