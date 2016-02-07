## Welcome to Ark

[![Build Status](https://api.travis-ci.org/arauk/ark.png)](https://travis-ci.org/arauk/ark)

Ark is a modern framework that includes everything needed to create
database-backed Java applications according to the
[Model-View-Controller (MVC)](http://en.wikipedia.org/wiki/Model-view-controller)
pattern. The initial version of the framework is entirely focused on the model
layer.

Understanding the MVC pattern is key to understanding Ark. MVC divides
your application into three layers, each with a specific responsibility.

The _Model layer_ represents your domain model (such as Account, Product,
Person, Post, etc.) and encapsulates the business logic that is specific to your
application. In Ark, database-backed model classes are derived from
`ActiveRecord` class. Active Record allows you to present the data from database
rows as objects and embellish these data objects with business logic methods.
Although most Ark models are backed by a database, models can also be ordinary
Java classes, or Java classes that implement a set of interfaces as provided by
the Active Model module. You can read more about ActiveRecord in its
[README](activerecord/README.md).

ActiveRecord, and ActionRequest can each be used independently outside
Ark. In addition to them, Ark also comes with ActiveSupport
([README](activesupport/README.md)), a collection of utility classes and
standard library extensions that are useful for Ark, and may also be used
independently outside the framework.


#### Contributing

We encourage you to contribute to all of our frameworks! Please check out the
[Contributing to Ark page](http://ark.arauk.com.br/contributing)
for guidelines about how to proceed.
[Join us!](http://ark.arauk.com.br/contributing/contributors)


### License

Ark is released under the
[MIT License](http://www.opensource.org/licenses/MIT).
