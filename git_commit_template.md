# Commit template

## Commit title

Title should be capitalized, and be a concise description of the changes made.

The title should complete the sentence:
> If applied, this commit will...

e.g. `Refactor GUI files`, `Optimize parser performance`

## Commit description

More detailed explanatory text, if necessary. Use if the commit makes a complicated change and the title is not enough to explain the change.

If included, the description should provide explanations for:
- Why is this change needed?
- How does this commit address the issue?
- What side effects does this change have?

Optionally can provide links to relevant issues/pull requests, articles, resources, members that worked on the commit, etc.

## Example commit
(Source: https://thoughtbot.com/blog/5-useful-tips-for-a-better-commit-message)
```
Redirect user to the requested page after login

https://trello.com/path/to/relevant/card

Users were being redirected to the home page after login, which is less
useful than redirecting to the page they had originally requested before
being redirected to the login form.

* Store requested path in a session variable
* Redirect to the stored location after successfully logging in the user

Authors: Person1, Person2
```
