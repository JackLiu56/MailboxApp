<h1 align="center">Email Mobile Terminal</h1>
Author: Ziyang Liu, Yuezhong Yan, Anil Mikkilineni, Lixing Jiang

Why choose this Application?

Limited functionality streamlines usages
    -Useful for limited or private access email accounts
    -Minimizes incorrect usages due to user error 
    -Reduces security risks
    -Decreases application processing
Quick implementation
    -Allows for rapid development and deployment 
    -Architecture allows for reliable data transfer
    -LiveData maintains states

<h1>Usage</h1>
Requirement: Must be a formal NJIT student with a valid NJIT email address

How to set up:

Step 1

    Go to https://directory.njit.edu/AdvancedSearch.aspx
    Login with your UCID and password

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Usage1.png)
    
Step 2

    Choose “Edit My Profile”

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Usage2.png)
    
Step 3

    Choose “E-Mail Settings”

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Usage3.png)

Step 4

    Set the password that to log in the App

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Usage4.png)
    





0.    Environment: Android Studio 3.5 && JDK 1.8

1.    Architecture: MVVM: Model-View-ViewModel
![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Picture1.png)
2.    UI
Login page:

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Picture2.png)

Fetch Mail page:

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Picture3.png)

Send email:

![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Picture4.png)

3.    Main(not all) functionalities: Retrieve emails, Send emails

Retrieve emails:

    FetchEmail: It is a model that contains related functionalities of logging in and retrieving emails.
        -FetchMailFragment: It is a ViewModel to retrieve and denote emails with list.
        -fragment_fetch_mail.xml: It is a view to retrieve emails.

Logic:
        Model is responsible to retrieve emails, and then fetches email content to ViewModel. ViewModel notifies views that “subscribe” this ViewModel to update.


    Send emails:
        -SendEmail: It is a model that contains related functionalities of sending emails.
        -SendMailFragment: It is a model to send emails.
        -SendMailViewModel: It is a ViewModel to send emails.
        -fragment_send_mail.xml: It is a view to send emails.
      
Logic:
        Model is responsible for sending emails. Set email content to be null after email is sent successfully. That is, set ViewModel to be null. ViewModel notifies views that “subscribe” this ViewModel to update.
      MyApplication: Global class to store username and password.
      
All classes related to Login:
![Image text](https://github.com/JackLiu56/MailboxApp/blob/master/image/Picture5.png)
           

        -LoginActivity: It is a controller to login with emails.
        -LoginFormState: Data validation state of the login form.
        -LoginResult: Authentication result: success (user details) or error message.
        -LoginViewModel: It is a ViewModel to login with emails
        -LoginViewModelFactory: It is ViewModel provider factory to instantiate   LoginViewModel. Required given LoginViewModel has a non-empty constructor.
     
     MainActivity: Proceed to this page when login success. This page also fetches FetchMailFragment and SendMailFragment. 

4.    Our understanding about MVVM (Correct me if I am wrong):
a)    Introduction to MVVM
MVVM is a software architectural pattern that helps organize code. It has 3 components: Model, View, and ViewModel. 

Generally speaking, Model holds the data. View holds the formatted data, such as structure, layout, appearance, etc., and delegates everything to the Model. ViewModel links between Model and View and makes things pretty. 

Reference: https://www.tutorialspoint.com/mvvm/mvvm_quick_guide.htm

b)    Comparisons between MVC, MVP, and MVVM
We actually have other two patterns to choose: MVC and MVP. 
MVC = Model View Controller
MVP = Model View Presenter
Model and View in these two act similarly as in MVVM.

Controller passes user’s data through the Model and passes results to View.

Presenter receives the input from users via View, then processes the user’s data with the help of Model and finally passes results to View. Presenter communicates with view through interface. Interface is defined in presenter class, to which it pass the required data. Activity/fragment or any other view component implement this interface and renders the data in a way they want.

ViewModel exposes methods, instructions, and other properties that help maintain the state of View, manipulate the Model as the result of actions on View, and trigger events on View. 

Reference: https://medium.com/@ankit.sinhal/mvc-mvp-and-mvvm-design-pattern-6e169567bbad

c)    Why do we pick MVVM?
Bi-directional data-binding between View and ViewModel allows automatic update of changes. Such data-binding ensures that the models and properties in the View-Model are in sync with the view.

Reference: https://medium.com/@ankit.sinhal/mvc-mvp-and-mvvm-design-pattern-6e169567bbad

d)    What is LiveData? (Can Skip)
LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component Observers that are in an active lifecycle state.

Reference: https://developer.android.com/topic/libraries/architecture/livedata#java

e)    What does LiveData do here in MVVM?
Generally, the source of data for LiveData is ViewModel. As data is updated, LiveData will let all of its Observers (such as Activity, Fragment, etc.) be aware of such change. However, LiveData does not “notify” all of its Observers blindly. LiveData first checks real-time states of Observers and then notifies those Observers in “active” state. If Observers are in “paused” or “destroyed” state, then they will not be notified. 

Since we use LiveData, we do not need to remove subscriptions on LiveData via onPause() or onDestroy() method. In addition, Observer will re-receive the updated data from LiveData if it goes to “resumed” state.

Reference: https://medium.com/androiddevelopers/livedata-beyond-the-viewmodel-reactive-patterns-using-transformations-and-mediatorlivedata-fda520ba00b7

https://developer.android.com/topic/libraries/architecture/livedata#java

https://developer.android.com/reference/android/app/Activity

https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State.html#RESUMED

f)    When should we notify View to show up data?
If a lifecycle becomes inactive, it receives the latest data upon becoming active again. 

Imagine, without LiveData, that when a result of request has already been returned back, an app has to check if Activity or Fragment is destroyed. If not destroyed, then update UI. Otherwise do not update. Then developer implements this 
function by writing bunch of Activity.isDestoyed(). However, with LiveData, an app does call-backs when Activity is onStart or onResume. 


Reference: https://developer.android.com/topic/libraries/architecture/livedata#java

5.    Some interface(s) and API(s) we think as important:
a)    interface Observer: 
package androidx.lifecycle;

/**
 * A simple callback that can receive from {@link LiveData}.
 *
 * @param <T> The type of the parameter
 *
 * @see LiveData LiveData - for a usage description.
 */
public interface Observer<T> {
    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    void onChanged(T t);
}

b)    interface List: store emails and email messages.
c)    javax.mail API: useful to send and retrieve emails.

