# Android Dev
Sai Kalyana Raman(U24553100)\
Shatrugna Rao Korukanti(U43517028)

---

## Overview

Our project is to build an utility android app. It is similar to the present day [Notion app](https://play.google.com/store/apps/details?id=notion.id). We aim to provide tools in assisting document generation and also integrate with google drive storage medium. We also plan to integrate live collaboration, p2p video and voice chat and workspace sharing. The app will be using markdown markup language to dynamically generate ui elements and for easy storage of the user document.

---

## Related Work

It is simlar to Notion app, and has few unique features. One which is integrating with Gdrive, and p2p video and voice chat. There could be a downgrade in the visual elements due to material design constraint in android.

---

## Requirement Analysis and Testing
 
| Title<br>        | View project details (Essential)                                                                                                                                                                                                                                                                   | View project details (Desirable)                                                                                            | View project details (Optional)                                                                                                    |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| Description      | Our project is to build an utility android app similar to [Notion app](https://play.google.com/store/apps/details?id=notion.id). We aim to provide tools in assisting document generation and also integrate with google drive storage medium.                                                     | Additionally, We plan to integrate live collaboration, p2p video and voice chat and workspace sharing as desirable options. | The app can also have markdown markup language to dynamically generate ui elements and also for easy storage of the user document. |
| Mockups          | <table><thead><tr><th>sample_main</th><th>sample_page1</th></tr></thead><tbody><tr><td>![sample](./ProjectSampleUI.jpeg)</td><td>![sample1](./ProjectSampleUI_1.jpeg)</td></tr><tr><td>![sample2](./ProjectSampleUI_2.jpeg)</td><td>![sample3](./ProjectSampleUI_3.jpeg)</td></tr></tbody></table> |                                                                                                                             |                                                                                                                                    |
| Acceptance Tests | UI components dynamic creation and data Retention of notion app in GDrive.                                                                                                                                                                                                                         | Testing of p2p networking, voice chat and live collaboration                                                                | markdown parser development                                                                                                        |
| Test Results     |                                                                                                                                                                                                                                                                                                    |                                                                                                                             |                                                                                                                                    |
| Status           |                                                                                                                                                                                                                                                                                                    |                                                                                                                             |                                                                                                                                    |

    
---

## Design and Implementation

- UI design and implementation
  - Jetpack Compose UI elements.
  - <img src="./Iteration-1 6.jpeg" width="200">
- Activities
  - There are 2 main jetpack UI pages MainPage, Page.
  - MainPage contains link to navigate other pages.
  - <img src="./Iteration-1 1.jpeg" width="200">
  - The Page can contain multiple components like textField, toDo checkbox, and link to navigate other pages.
  - <img src="./Iteration-1 1.jpeg" width="200">
- Third party APIs
  - usage of Jackson Core for json serialization and deserialization.
- Database schema, data storage
  - The data of the pages and info are stored in the data store shared preferences. There will be a plan to move the db api to android room api.

---

## Project Structure
| Iteration 1              |
|--------------------------|
| ![](./ProjStructure.png) |


---

## Timeline

| Iteration | Application Requirements                                                                                                                                                                                                                                                                                               | Android Components and Features | member 1 contribution/tasks                                              | member 2 contribution/tasks                                                                                  |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------|--------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| 1         | <table><thead><tr><th>Essential</th><th>Desirable</th><th>Optional</th></tr></thead><tbody><tr><td>App Layout<br>UI tools development</td><td>Complex UI development<br> such as table and kanban board</td><td>Hyperlinks and image ui integration.</td></tbody></table>                                              | Jetpack Compose                 | Design and implement app layout, Functional Implementation of components | Navigation implementation, data storage of the UI components. Testing of the app and performing minor fixes. |
| 2         | <table><thead><tr><th>Essential</th><th>Desirable</th><th>Optional</th></tr></thead><tbody><tr><td>Serialization of UI data<br>and integrating gdrive api.</td><td>Integrating voice and video chat using webrtc protocol.</td><td>serialization of data in the form of markdown markup language.</td></tbody></table> | Google Drive Kotlin APIs'       | GDrive Integration                                                       | P2P Networking implementation                                                                                |
| 3         | <table><thead><tr><th>Essential</th><th>Desirable</th><th>Optional</th></tr></thead><tbody><tr><td>Unit testing of functional components and gdrive api.</td><td>Live collaboration of the document with invited friends.</td><td>None</td></tbody></table>                                                            | JUNIT and UI Testing            | JUNIT test cases and live Testing of the app                             | Live Collaboration feature implementation                                                                    |

---

## Future Work (Optional)
| Iteration | Missed Implementation                                                                                            |
|-----------|------------------------------------------------------------------------------------------------------------------|
| 1         | Image component implementation missed due to time constraint.                                                    |
|           | Table component implementation missed due to not being able to figure out the implementation in jetpack compose. |

---

## References

[Notion app](https://play.google.com/store/apps/details?id=notion.id)
