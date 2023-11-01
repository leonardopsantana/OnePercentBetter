## App Architecture

This app follows a MVVM architecture pattern throughout the project. Below is the architecture
diagram for the Login Screen, though every screen in the app should follow the same organization.

![App architecture diagram](assets/AppModules.jpg)

This document is intended to clarify each of types of layers in the above image and their
responsibilities.

## Repository

A repository is the piece of the layer that is responsible for making data requests. This could be a
remote server, a local preferences, database, etc. The repository should only request and receive
data, it should have any side effects such as calling a different repository. The repository should
be responsible for mapping information from DTO (data transfer object) models to domain specific
data classes.

Repositories should not be responsible for any data manipulation of a response. For example, if a
repository is requesting a list of users, but a screen only care about users with a certain
property,
the use case should be responsible for filtering the list accordingly.

## Use Case

The inspiration to use Use Cases comes from
this [blog post](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576).

The purpose of a use case is to perform any specific action that occurs on a screen. This could be
fetching or submitting data, filtering items, etc. Domain specific business logic like this deserves
its own layer in the app's architecture.

A repository is only responsible for requesting and receiving data. A ViewModel is only responsible
for taking the results of an action, and mapping into a ViewState. The Use Case is responsible for
whatever happens in between - consuming from data requests, and mapping that into a relevant result
for ViewModel to handle.

## ViewModel

This layer is responsible for connecting the view with any relevant use cases. The ViewModel
consumes UI events, triggers a necessary use case, and process the use case's response into a
ViewState that is exposed for the screen to render.

A ViewModel may also have certain actions that are triggered the moment it's created.

## View

The View layer is solely responsible for being able to display data on the UI. It can consume a 
view state which specifies what information should be rendered. Any UI events such as inputs, button
clicks, etc. will be passed to the ViewModel which determines what action needs to happen.

If a screen has multiple complex views or multiple responsibilities, it's possible to reference
multiple ViewModel dependencies.