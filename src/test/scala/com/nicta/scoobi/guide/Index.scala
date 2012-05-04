package com.nicta.scoobi.guide

/**
 * This class generates the index.html page used for landing users on http://NICTA.github.com/scoobi
 */
class Index extends ScoobiPage { def is = args.report(notoc=true) ^  "Welcome!".title ^
                                                                                                                        """
[Hadoop MapReduce](http://hadoop.apache.org/) is awesome, but it seems a little bit crazy when you have to write [this](http://wiki.apache.org/hadoop/WordCount) to count words. Wouldn't it be nicer if you could simply write what you want to do:

      object WordCount extends ScoobiApp {
        val lines = fromTextFile(args(0))

        val counts = lines.flatMap(_.split(" "))
                          .map(word => (word, 1))
                          .groupByKey
                          .combine(_+_)

        persist(toTextFile(counts, args(1)))
      }

This is what Scoobi is all about. Scoobi is a Scala library that focuses on making you more productive at building Hadoop applications. It stands on the functional programming shoulders of Scala and allows you to just write **what** you want rather than **how** to do it.

Scoobi is a library that leverages the Scala programming language to provide a programmer friendly abstraction around Hadoop's MapReduce to facilitate rapid development of analytics and machine-learning algorithms.

### Install

See the [install instructions](SCOOBI_GUIDE/Quick%20Start.html#Installing+Scoobi) in the QuickStart section of the [User Guide](SCOOBI_GUIDE/User%20Guide.html).

### Features

 * Familiar APIs - the `DList` API is very similar to the standard Scala `List` API

 * Strong typing - the APIs are strongly typed so as to catch more errors at compile time, a
 major improvement over standard Hadoop MapReduce where type-based run-time errors often occur

 * Ability to parameterize with rich [data types](SCOOBI_GUIDE/Data%20Types.html) - unlike Hadoop MapReduce, which requires that you go off implementing a myriad of classes that implement the `Writable` interface, Scoobi allows `DList` objects to be parameterized by normal Scala types including value types (e.g. `Int`, `String`, `Double`), tuple types (with arbitrary nesting) as well as **case classes**

 * Support for multiple types of I/O - currently built-in support for [text](SCOOBI_GUIDE/Input%20and%20Output.html#Text+files), [Sequence](SCOOBI_GUIDE/Input%20and%20Output.html#Sequence+files) and [Avro](SCOOBI_GUIDE/Input%20and%20Output.html#Avro+files) files with the ability to implement support for [custom sources/sinks](SCOOBI_GUIDE/Input%20and%20Output.html#Custom+sources+and+sinks)

 * Optimization across library boundaries - the optimizer and execution engine will assemble Scoobi code spread across multiple software components so you still keep the benefits of modularity

 * It's Scala - being a Scala library, Scoobi applications still have access to those precious Java libraries plus all the functional programming and consise syntax that makes developing Hadoop applications very productive

 * Apache V2 licence - just like the rest of Hadoop

### Getting Started

To get started, read the [getting started steps](SCOOBI_GUIDE/Quick%20Start.html) and the section on [distributed lists](SCOOBI_GUIDE/Distributed%20Lists.html). The remaining sections in the [User Guide](SCOOBI_GUIDE/User%20Guide.html) provide further detail on various aspects of Scoobi's functionality.

The user mailing list is at <http://groups.google.com/group/scoobi-users>. Please use it for questions and comments!

### Community

 * [Issues](https://github.com/NICTA/scoobi/issues)
 * [Change history](https://github.com/NICTA/scoobi/blob/SCOOBI_BRANCH/CHANGES.md)
 * [Source code (github)](https://github.com/NICTA/scoobi)
 * [API Documentation](http://nicta.github.com/scoobi/SCOOBI_BRANCH/index.html)
 * [Examples](https://github.com/NICTA/scoobi/tree/SCOOBI_BRANCH/examples)
 * Mailing Lists: [scoobi-users](http://groups.google.com/group/scoobi-users), [scoobi-dev](http://groups.google.com/group/scoobi-dev)
                               """

}
