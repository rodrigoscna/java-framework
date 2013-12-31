## Welcome to inAndroid

inAndroid is a modern framework that includes everything needed to create database-backed Android
applications according to the
[Model-View-Controller (MVC)](http://en.wikipedia.org/wiki/Model-view-controller)
pattern. The initial version of the framework is entirely focused on the model layer.

Understanding the MVC pattern is key to understanding inAndroid. MVC divides your application into
three layers, each with a specific responsibility.

The _Model layer_ represents your domain model (such as Account, Product, Person, Post, etc.) and
encapsulates the business logic that is specific to your application. In inAndroid, database-backed
model classes are derived from `ActiveRecord` class. Active Record allows you to present the data
from database rows as objects and embellish these data objects with business logic methods. Although
most inAndroid models are backed by a database, models can also be ordinary Java classes, or Java
classes that implement a set of interfaces as provided by the Active Model module. You can read more
about Active Record in its [README](active-record/README.md).

Active Record, and Action Request can each be used independently outside inAndroid. In addition to
them, inAndroid also comes with Active Support ([README](active-support/README.md)), a collection of
utility classes and standard library extensions that are useful for inAndroid, and may also be used
independently outside the framework.

## Contributing

We encourage you to contribute to all of our frameworks! Please check out the
[Contributing to inFrameworks page](http://www.inframeworks.org/contributing)
for guidelines about how to proceed.
[Join us!](http://www.inframeworks.org/contributing/contributors)

## Code Status

* [![Build Status](https://api.travis-ci.org/inframeworks/inandroid.png)](https://travis-ci.org/inframeworks/inandroid)

## License

inAndroid is released under the [MIT License](http://www.opensource.org/licenses/MIT).
