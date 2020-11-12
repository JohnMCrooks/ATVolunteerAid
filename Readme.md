# Roadmap

### MVP 1.0 (completed 11/11/20):
- Setup Local DB w/ Room
- Simple Schema v2=(Id, Date, Lat, Long, problemType)
- Request permissions from user for location uasge
- Report problems (insert into DB)
- Clear problems from DB
- Use LiveData and recyclerView to populate entries (scrollable, but non-interactive)
- Navigation between List view, Map, Report Problem screens
- Populate map with locations from database

**MAKE A NEW BRANCH FOR EACH OF THESE**
## LOCAL MVP 1.1.0 - add common architectural design to the app
- Refactor Fragments to use proper MVVM architecture (Model, View, ViewModel)
- implement way to preserve app integrity on screen rotations

## LOCAL MVP 1.1.1 - Update Schema for
- Update Schema v3 - Might require more research on best way to setup user/problem relationship but the Goal is to
                     eventually allow users to sort/filter by their own reports, others reports, report type, etc.
---- table_user: uuid, date_joined
---- table_problemReport: uuid, Lat, Long, problemType, resolved, reportedBy (Join on user, many reports to one user)

## LOCAL MVP 1.1.2
- Refactor recyclerView to use Cards for Problem info instead of TextView
- Add way to mark Problem as resolved on individual cards (button or swipe, maybe?)

## LOCAL  MVP 1.1.3 - Map-centric upgrades
- filter map markers to only show unresolved reports
- Add User location/locate me button to map
- Auto zoom to user location on map open

## LOCAL MVP 1.1.4 - Optional based on interest/boredom levels
- research filters in the map view
---- toggle all/unresolved/resolved
---- toggle to show by Type of problem
- research customized tags (color/shapes/whatever) based on type

## REMOTE MVP 1.0 - API creation & synchronization strategies
- Research & Implement backend for remotely hosted API - https://kotlinlang.org/docs/reference/server-overview.html
- Reasearch & implement way to keep local DB and remote DB synced.