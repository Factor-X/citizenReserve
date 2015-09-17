# Source root identification

We need to define the app folder as a source root.

Right click > Mark Directory As > Sources Root

# Scala installation

The Scala plugin is not installed by default. Open the AngularCompiler.scala file. A message is displayed at the top of
the editor to propose the installation of the Scala plugin. Install the plugin and let Intellij IDEA restart.

Now, we have at the top of the same file a message suggesting to install the Scala SDK. We go to:

http://www.scala-lang.org/download/

And install Scala (at the date of this writing in 2015.09, Scala 2.11.7).

To add Scala support, we will need to create a library by pointing the IDE to the Scala install folder.

We are now treating another message at the top of the editor telling that AngularComposite.scala is SBT build file.
We import the project as suggested.

# Play framework support

Some files, such as AccountController.java, can't resolve some symbols that belong to Play. We need to activate the
support. On the citizens-reserve project node:

Right click > Add Framework Support ... > Play 2.x

The resolution problem should disappear.
