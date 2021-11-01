# ATVolunteerAid

### About
#### The idea for this app was born after talking with my brother in law who is a forest ranger in NC. He expressed concern that they were having a tough time with getting people to report trail issues and jokingly wished for an app that could solve the problem. This seemed like a great app to dive into to help expand my Android development skills. I had never worked with the Maps SDK, or any firebase integrations in general, prior to starting this project. 

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

![AT_homescreen](https://user-images.githubusercontent.com/19226510/139610104-49161045-5712-4f47-969a-c912326a09fa.png) ![AT_problem_list](https://user-images.githubusercontent.com/19226510/139612066-b911c4eb-f933-4026-a58c-476241e7d170.png)

![AT_mapview](https://user-images.githubusercontent.com/19226510/139610230-d6d6ae2a-c912-444f-b3f3-d6a49d03bae8.png) ![AT_Map_zoomemd_in](https://user-images.githubusercontent.com/19226510/139609939-378ce70b-016f-426f-b54a-f312b160854c.png)

![AT_report_problem2](https://user-images.githubusercontent.com/19226510/139612138-22e0f523-53e7-4f01-9a62-f32ae9ef6356.png)

## WIP - LOCAL MVP 1.1.0 - add common architectural design to the app 
Goals:
- Refactor Fragments/Project to use databinding, MVVM architecture
  - References:
    - https://developer.android.com/jetpack/guide
    - https://www.raywenderlich.com/636803-mvvm-and-databinding-android-design-patterns
    - https://medium.com/@er.ankitbisht/mvvm-model-view-viewmodel-kotlin-google-jetpack-f02ec7754854
    - https://proandroiddev.com/mvvm-with-kotlin-android-architecture-components-dagger-2-retrofit-and-rxandroid-1a4ebb38c699

## LOCAL MVP 1.1.1 - Update Schema for
- Update Schema v3 - Might require more research on best way to setup user/problem relationship but the Goal is to
                     eventually allow users to sort/filter by their own reports, others reports, report type, etc.
  - table_user: uuid, date_joined
  - table_problemReport: uuid, Lat, Long, problemType, resolved, reportedBy (Join on user, many reports to one user)

## LOCAL MVP 1.1.2
- Refactor recyclerView to use Cards for Problem info instead of TextView
- Add way to mark Problem as resolved on individual cards (button or swipe, maybe?)

## LOCAL  MVP 1.1.3 - Map-centric upgrades
- filter map markers to only show unresolved reports
- Add User location/locate me button to map (complete)
- Auto zoom to user location on map open (complete)

## LOCAL MVP 1.1.4 - Optional based on interest/boredom levels
- research filters in the map view
  - toggle all/unresolved/resolved
  - toggle to show by Type of problem
- research customized tags (color/shapes/whatever) based on type

## REMOTE MVP 1.0 - API creation & synchronization strategies
- Research & Implement backend for remotely hosted API - https://kotlinlang.org/docs/reference/server-overview.html
- Reasearch & implement way to keep local DB and remote DB synced.




