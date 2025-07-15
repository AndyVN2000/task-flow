# task-flow

## Relevant tutorials

### Many-To-Many
Handling many-to-many relationships using JPA: https://www.baeldung.com/jpa-many-to-many


### Many-To-One
Handling many-to-one relationships using JPA: https://www.bezkoder.com/jpa-manytoone/

It is typically preferable to use the `@ManyToOne` annotation rather than `@OneToMany`. 
The reasons are as follows: <br>
With `@OneToMany`, we need to declare a collection (Comments) inside parent class (Tutorial), we cannot limit the size of that collection, for example, in case of pagination. <br>
With `@ManyToOne`, you can modify the Repository:
- to work with Pagination
- or to sort/order by multiple fields

I do not completely understand these reasons. I might need to find other sources.

