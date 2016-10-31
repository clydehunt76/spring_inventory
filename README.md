# Inventory SPA

## Problems

Complete these exercises in order, using `git` to checkpoint your work as you go along.

As always, it is recommended that you read this entire problem set prior to beginning it so that you understand what you are building towards. Everything presented here should use DOM manipulation and an Express API to build a Single Page Application (SPA).

### Starting point

Use the Express generator to Create a new Express application in the directory that you cloned your forked repository to. You may call this application `products_and_categories` (or whatever you want that represents what it is). For this application you will not have to worry about style until stretch goals. Here are the features you are looking to build:

#### Feature 0

As a developer, I would like to have an API for creating and retrieving `categories`. A `category` document consists of the category `name` and its `_id`.

_Question_ Is this one story or multiple?

#### Feature 1

As a developer, I would like to have an API for creating, retrieving, and updating `products`. A `product` document consists of the `name`, `price`, `category` and `description`.

_Question_ Is this one story or multiple?

#### Feature 2

As an end user, I would like to have a single page application (SPA) flow for creating categories on the fly.

_Question_ Is this one story or multiple?

#### Feature 3

As an end user, I would like to have a single page application (SPA) flow that allows me to create products, providing them with an associated category in the same flow. In this SPA, I should be able to create products and associate them with one or more categories (_hint_: this means checkboxes), as well as creating new categories. This is a rough mockup of what this looks like:

<center>
  ![Wireframe of Products and Categories](https://galvanize.mybalsamiq.com/mockups/3831920.png?key=0a3a49896fe5fdecbd75cdc81da42a7e23eb14d6)
</center>

_Question_ Is this one story or multiple?

#### Feature 4

As an end user, I would like to have a more detailed view appear when I click on one of the specific product links in the Product List. This is a rough mockup of what this looks like:

<center>
  ![Wireframe of Products and Categories with show view](https://galvanize.mybalsamiq.com/mockups/3831926.png?key=2b0cfeabae56792987a85f13fb790ad793b68525)
</center>

#### Stretch Features

1. Can you make this have a good UX? What changes would make a better user experience? One small idea for a starting point is something like placeholder text that tells the user what to enter in each form field.
1. How does a user update a product? What's a way to incorporate that workflow and still have the user live in one single page?
1. As a business owner, I would like for the Single Page Application (SPA) to be well-styled so that I feel warm and fuzzy about the hard work my developers do.
1. When I add a new category, does it appear as a checkbox on the product immediately, or does the user need to refresh the page? What if multiple users are using the site at once? Can you think of a way to make it so all users can see the same thing at once?
1. Research and select a templating library like knockout.js or handlebars, and refactor your code to use that library. Would using that library have made your development easier?

#### Setup

Fork and clone this repository so that you may make commits of your work along the way. To Fork a repository, click on the "Fork" button at the top right of this page. Next, from the Forked repository page, copy the clone URL and clone this locally using `git clone [URL copied from forked repository]`. When you have made commits, use `git push origin master` to push them up to the remote (Github).

## Reflection

1. Did we write tests before code at all points in time?
1. Did we communicate openly and effectively?
1. Did one member of the pair dominate the session?
1. Was there a specific pairing technique that was effective?
