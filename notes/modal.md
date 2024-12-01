# Modal

## Show messages...

The idea is to use separate layers for each type of Modal:
1. A layer for CookieDisclaimer, z-index = 1001, say
2. A layer for Dialogs, z-index = 1002, say
3. A layer for Errors, z-index = 1003, say
4. The semi-transparent background, z-index = 1000, say 

Tasks:
1. So the modal itself gets a target layer as property
2. Layers need to be setup
3. Fix usages

-> Done