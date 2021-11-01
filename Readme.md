# Roadmap

### MVP 1.0 (completed 11/11/20):
- Setup Local DB w/ Room
- Simple Schema, single table, v2=(Id, Date, Lat, Long, problemType)
- Request permissions from user for location uasge
- Report problems (insert into DB)
- Clear problems from DB
- Use LiveData and recyclerView to populate entries (scrollable, but non-interactive)
- Navigation between List view, Map, Report Problem screens
- Populate map with locations from database

**DON"T FORGET TO USE A NEW BRANCH FOR EACH OF THESE**
## LOCAL MVP 1.1.0 - add common architectural design to the app
Goals:
- Add Firebase foundation to use later on.
- Refactor Fragments/Project to use databinding, MVVM architecture
  - References:
    - https://developer.android.com/jetpack/guide
    - https://www.raywenderlich.com/636803-mvvm-and-databinding-android-design-patterns
    - https://medium.com/@er.ankitbisht/mvvm-model-view-viewmodel-kotlin-google-jetpack-f02ec7754854
    - https://proandroiddev.com/mvvm-with-kotlin-android-architecture-components-dagger-2-retrofit-and-rxandroid-1a4ebb38c699

## LOCAL MVP 1.1.1 - Update Schema -
- Update Schema v3 - Might require more research on best way to setup user/problem relationship but the Goal is to
                     eventually allow users to sort/filter by their own reports, others reports, report type, etc.
  - table_user: uuid, date_joined
  - table_problemReport: uuid, Lat, Long, problemType, resolved, reportedBy (Join on user, many reports to one user)

## LOCAL MVP 1.1.2
- Refactor recyclerView to use Cards for Problem info instead of TextView
- Add way to mark Problem as resolved on individual cards (button or swipe, maybe?)

## LOCAL  MVP 1.1.3 - Map-centric upgrades
- filter map markers to only show unresolved reports (displays all reports at the moment since they can only be deleted not completed, requires DB update in 1.1.1 to be completed first)
- Done - Add User location/locate me button to map
- Done - If user clicks Map, it opens map to display whole AT trail.
- Done - If user clicked on a report from the list it takes user to that problem on the map

## LOCAL MVP 1.1.4 - Optional based on interest/boredom levels
- Refactor MapActivity to include fragment for changing data filters
- research filters in the map view
 - toggle all/unresolved/resolved
 - toggle to show by Type of problem
- Done - research customized tags (color/shapes/whatever) based on type
- add photos of the issue to map w/ custom info windows - https://developers.google.com/maps/documentation/android-sdk/infowindows
 - update associated fragments/views/flows to include ability to add a photo


## REMOTE MVP 1.0 - API creation & synchronization strategies
- Research & Implement backend for remotely hosted API -
 - Resources - https://kotlinlang.org/docs/reference/server-overview.html
 - simple hello world tutorial w/ Springboot - https://kotlinlang.org/docs/tutorials/spring-boot-restful.html
- Research & implement way to keep local DB and remote DB synced.