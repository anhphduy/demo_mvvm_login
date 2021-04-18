# Android MVVM Architecture: Demo Login App

This is an Demo app that implements MVVM architecture and using Dagger2, Room, Coroutines.

<br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/50766792/115141389-26067d80-a066-11eb-9f7b-4dcbb3187d24.png" width="200">
  <img src="https://user-images.githubusercontent.com/50766792/115141436-5d752a00-a066-11eb-9419-8a01e1a28d0b.png" width="200">
  <img src="https://user-images.githubusercontent.com/50766792/115141593-70d4c500-a067-11eb-8a7e-65018ebbd185.png" width="200">
  <img src="https://user-images.githubusercontent.com/50766792/115141590-6f0b0180-a067-11eb-85d9-72e6b02d3429.png" width="200">
</p>
<br>
<br>

#### The app has following packages:
1. **base**: It contains abstract class to that have common functions.
2. **common**: It contains common classes
3. **custom**: It contains some custom views
4. **data**: It contains all the data accessing and manipulating components.
5. **di**: Dependency providing classes using Dagger2.
7. **ui**: View classes along with their corresponding ViewModel.
8. **utils**: Utility classes.

### Code flow:
#### Splash screen: 
1. SplashActivity will call navigate() function in SplashViewModel.
2. navigate() function in SplashViewModel: it will call hadLoginUser() from UserRepository to check had user login before and return a result 
3. _moveCommand liveData_ from SplashViewModel will be set with the result that returned from UserRepository.
4. SplashActivity will observe moveCommand from SplashViewModel to navigate.

#### Login screen: Login flow
1. It will check for email and password, if it's ok, it will call login() function from LoginViewModel.
2. login() function in LoginViewModel: it will launch a coroutine to IO dispatchers to call login function from AuthenRepository.
3. _moveCommand liveData_ from LoginViewModel will be set with DataResult (it has state loading, success, error)
4. Logic to login will be handle in AuthenRepository. (get user with username and password from local database, if return null -> login error, if not null, login success)
5. LoginFragment will observe moveCommand from LoginViewModel to navigate.

#### Signup screen: signup flow
1. It will check for username, email and password, if it's ok, it will call signUp() function from SignupViewModel.
2. signUp() function in SignupViewModel: it will launch a coroutine to IO dispatchers to call signUp function from AuthenRepository.
3. _moveCommand liveData_ from SignupViewModel will be set with DataResult (it has state loading, success, error)
4. Logic to signUp will be handle in AuthenRepository. (get user with username and password from local database, if return null -> save newUser to local database, save to sharedPreference, signUp success, if return user -> signUp error)
5. SignupFragment will observe moveCommand from SignupViewModel to navigate.

#### Main screen: 
1. MainActivity will call getCurrentUserLogin() function in MainViewModel.
2. getCurrentUserLogin() function in MainViewModel: it will call getCurrentLoginUser() from UserRepository to get current user and return it 
3. userName from MainViewModel will be set with the current user name -> bind it to view

#### Classes have been designed in such a way that it could be inherited and maximize the code reuse.

### Library reference resources:
1. Dagger2: https://github.com/MindorksOpenSource/android-dagger2-example
2. Room: https://developer.android.com/topic/libraries/architecture/room.html
3. Coroutines: https://developer.android.com/kotlin/coroutines
