echo '# Fitness Center Membership System 💪

📊 **Presentation**  
View the project presentation here:  
👉 [Presentation Link]([https://www.canva.com/design/YOUR_DESIGN/edit](https://www.canva.com/design/DAGnK5zSe7A/ffON9ASmgXTLB8xBlZe-KQ/edit?utm_content=DAGnK5zSe7A&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton))  

## Overview  

A Java console application for managing fitness center members and their workout sessions. The system allows registering new members, viewing and updating their information, tracking workouts, and generating reports. All data is stored in a CSV file (`fitness_members.csv`).  

## Features  

- **Register Member**: Add new member with ID, name, and email  
- **View Member Info**: Show member details and workout history  
- **Update Info**: Modify member name or email  
- **Delete Member**: Remove member from system  
- **List All Members**: Display all registered members  
- **Add Workout**: Record workout details (date, exercise, sets, weight)  
- **Generate Report**: Show statistics about members and workouts  
- **Save Data**: Store all data in CSV file  
- **Load Data**: Automatically load data on startup  

## Requirements  

- Java Development Kit (JDK) version 8 or higher  
- `fitness_members.csv` file will be created automatically  

## How to Run  

1. Download `Main.java` file  
2. Open terminal in the file directory  
3 юUse the menu to interact with the system
![image](https://github.com/user-attachments/assets/25250d03-1741-4f61-b3ea-4bd600c66487)


Data Structure
Member class
Stores member information:

id - unique identifier
name - full name
email - contact email
detailedWorkouts - list of workouts

## Workout class

Represents a workout session:
day, month, year - workout date
exerciseName - exercise name
sets - number of sets
weight - weight used (kg)
![image](https://github.com/user-attachments/assets/dcffa284-ceac-4372-a85f-f1dfd9cfe958)

## Validation

Email: Valid email format check
ID: Unique identifier verification
Workout Data: Numeric field validation
Date: Proper day/month/year validation

## Data Storage
Data is saved in CSV format:
 **memberId,name,email,workout1;workout2;...**  
Each workout is formatted as:
**day-month-year|exerciseName|sets|weight**  
## Future Improvements
Detailed reports (workouts by period, popular exercises)
Membership expiration dates
Payment tracking
Graphical user interface
Backup functionality
## Example Usage
![image](https://github.com/user-attachments/assets/fc9d8b8a-88f5-4301-97ca-010e50a2ea7e)



