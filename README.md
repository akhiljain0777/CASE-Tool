# Computer Aided Software Engineering (CASE) Tool:

## Software requirements specification

We need to develop a software for automating various activities associated with structured software analysis
and design. The summary of the requirements for this CASE tool are the following:

### Structured Analysis:

* The case tool should support a graphical interface and the following features.
* The user should be able to draw bubbles, data stores, and entities and connect them using data flow
arrows. The data flow arrows are annotated by the corresponding data names.
* Should support editing the data flow diagram.
* Should be able to create the diagram hierarchically.
* The user should be able to determine balancing errors whenever required.
* The software should be able to create the data dictionary automatically.
* Should support printing the diagram on a variety of printers.

### Structured Design:

* The user should be able to draw modules, control arrows, and data flow arrows. Also, a symbol for library
modules should be provided. The data flow arrows are annotated with the corresponding data name.
* The user should be able to associate a module with some bubbles of the DFD. It should be possible to
check if all the bubbles are assigned to some module and also whether each module is assigned at least
one bubble.
* The user should be able to modify his design. Please note that when he deletes a data flow arrow, its
annotated data name automatically gets deleted.
* For large software, modules may be hierarchically organized and clicking on a module should be able to
show its internal organization.
* The user should be able to save his design and also be able to load previously created designs.

