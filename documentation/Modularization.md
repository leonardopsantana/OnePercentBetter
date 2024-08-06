# Modularization

The OPB application uses a multi module structure. Creating an android app with multiple modules
can help with optimizing local build times, keeping the codebase organized, and hiding the
implementation details from modules that don´t need it.

 The overall structure of modules inside the OPB app can be seen in this diagram. Skydoves images?

The general principle behind the module design is this:

1. A module to hold all of our core model classes used throughout application (core-models).
2. A module to hold all of our data layer logic used by the application (core-data).
3. A module to hold all of our of UI components and theme information (core-ui).
4. A module for each individual feature (typically a screen) of the application.
5. An app module that depends on each feature module connects them accordingly.

As this application grows in complexity, we may want to more modules for isolated concepts
our app could use. For example, we may one day adds an 'analytics' or 'error-reporting' module
when those tools are implemented. This concept can be seen today by the lint-checks module.
