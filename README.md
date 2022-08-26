# best-port-to-play

## Introduction
The main idea of this site is to collect information about the differences between different versions/ports of games and compare them. The initial idea is for each game to indicate on which platform the most complete version was released (with all the DLCs, with additional modes, etc.), the most stable version (Sonic’ 06 on Xbox 360 works a little better than on PS3) and the most beautiful version. There can be more than one option in each category, as long as playing on multiple platforms is no different. In the “Most complete” category, there may not be a clear “winner” if multiple versions have exclusive features, then “none, closest: " is indicated, and their differences are listed in a comment.

## Contribution
It is impossible to collect information about all existing games alone, so if you know about a game that is not yet in the database, or have something to add to existing information, do not be too lazy to spend a couple of minutes and create a PR. I appreciate your contribution to the history of the gaming industry :) (c) NetL

## How to contribute
Just open data/games.scv with notepad or any spreadsheet program, add/change information and make a PR. Currently file has format:
```
game title;other editions;all platforms where game exist;platfroms with the fullest editions;comments;platforms with the most stable editions;comments;platforms with the greates look;comments
```
Please note that "greatest look" does not always mean the newest. In some remasters, new shaders are added to old models, and this only spoils the overall look. Yes, this is a matter of taste, each such ambiguous case will be discussed in PR.

Currently CI/CD configured to parse and build pages from csv automatically but number of columns in csv can not grow without changes in convertation script (/csv-to-md folder).
