## Welcome to AtlasAndroid

AtlasAndroid is a modern framework that includes everything needed to create
database-backed Android applications according to the
[Model-View-Controller (MVC)](http://en.wikipedia.org/wiki/Model-view-controller)
pattern. The initial version of the framework is entirely focused on the model
layer.

Understanding the MVC pattern is key to understanding AtlasAndroid. MVC divides
your application into three layers, each with a specific responsibility.

The _Model layer_ represents your domain model (such as Account, Product,
Person, Post, etc.) and encapsulates the business logic that is specific to your
application. In AtlasAndroid, database-backed model classes are derived from
`ActiveRecord` class. Active Record allows you to present the data from database
rows as objects and embellish these data objects with business logic methods.
Although most AtlasAndroid models are backed by a database, models can also be
ordinary Java classes, or Java classes that implement a set of interfaces as
provided by the Active Model module. You can read more about ActiveRecord in its
[README](activerecord/README.md).

ActiveRecord, and ActionRequest can each be used independently outside
AtlasAndroid. In addition to them, AtlasAndroid also comes with ActiveSupport
([README](active-support/README.md)), a collection of utility classes and
standard library extensions that are useful for AtlasAndroid, and may also be
used independently outside the framework.

## Contributing

We encourage you to contribute to all of our frameworks! Please check out the
[Contributing to Atlas page](http://atlas-tech.org/contributing)
for guidelines about how to proceed.
[Join us!](http://atlas-tech.org/contributing/contributors)

## Code Status

* [![Build Status](https://api.travis-ci.org/atlas-tech/atlas-android.png)](https://travis-ci.org/atlas-tech/atlas-android)

## License

AtlasAndroid is released under the
[MIT License](http://www.opensource.org/licenses/MIT).
