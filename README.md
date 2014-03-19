jenseits-von-theben
===================

Advanced Java M1 MIAGE

* Scrum panel: https://trello.com/b/enM9BP2E/projet-jenseits-von-theben

Use of Git and Github
--------------
* Do not forget to often check and eventually merge master branch (or desired part) with your local work to avoid outdated works and stay in the dev line
* Never push anything on remote/master if your are not allowed!

### Create and connect my project with the repository
* Create project (we use Maven) on local
* Download the .gitignore https://github.com/mbret/jenseits-von-theben/blob/master/.gitignore and put inside root project folder
* Make a first commit
* Pull the remote/master (normally you should be asked to merge your current work with master, press ok or merge)
* You got the latest remote/master version in your local/master branch
* Now you have to switch to your branch (with your name) and start to work inside

There is no doubt about an easiest way with cloning but I never tried :D

### Merge a branch with my current branch
* `git merge master` will merge local/master with your current local/branch
* Netbeans: `git > Branch\Tag > Merge Revision..` will allow you to merge local or remote branch with your current local/branch

Branch
-------
| Name	  | Detail |
| ------------- | ------------- |
| release  | latest runnable project  |
| master  | latest project build merged from dev (only the repository owner can manage this  |
| dev  | merged current works for all developpers (all developpers have access)  |
| maxime, gael, richard, anne, david | personnal branch, used to work on defined part |

