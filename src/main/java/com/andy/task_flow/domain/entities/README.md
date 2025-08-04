# My thoughts

I had to implement the feature of project being able to be archived, meaning that the project is immutable.
So the class has different behaviour depending on its archived state.
I got the idea of using compositional design, where I define a `Project` interface.
A `ProjectImpl` implements `Project` and represents the normal Project that is not yet archived.
An `ArchivedProject` also implements `Project` and is the Project that has been archived and is immutable.

This design comes with disadvantages:

1. Type ambiguity at runtime (This could be solved with type-casting)
2. Complicates Persistence (This is the scariest one for me. I will try to experience the consequenses of my chosen design to be better)
