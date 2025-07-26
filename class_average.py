from array import *
import math

# Print the developer name and header information
print("========================================================================")
print("                    Developer: Cindy Kahl")
print("========================================================================")
print('                  School Name: Winston Churchill High School')
print('                      Teacher: Mrs. Hazel Meadowglen')
print('                Semester/Year: Spring 2023')
print("========================================================================")

# List of students
students_list = ['Carlos Jackson', 'Monte Solaris', 'Jacob Sean', 'Molly Albertson', 'Susan Hutchingson', 'Coby Arnold', 'Maria Blurgson', 'Parker Harris', 'Melroy Peraira Harris Williams', 'Joseph Makenzie', 'Amelia Proctor', 'Eleanor Stuart', 'Sophia Aurora', 'Luna Zuckerman', 'Grand Total']

# List of subjects
subject_list = ['English', 'Math', 'History', 'Geography', 'Science', 'Programming']

# Create a 2-dimensional list to store the grades
num_students = 16
num_subjects = 7
grades_table = [[0]*num_subjects for i in range(num_students)]

# Open the grades file and read the scores
with open('grades_input.dat', 'r') as f:
    for line in f:
        code, score = line.strip().split()
        row, col = int(code[:2]), int(code[2:])
        grades_table[row][col] = float(score)

# Print the header row
for subject in subject_list:
    print("{:<15}".format(subject), end="")
print()
print("========================================================================")

# Print the grades table with letter grades
for row in range(num_students):
    if row == num_students - 2:
        print('------------------------------------------------------------------------')
    elif row == num_students - 1:
        print("{:<15}".format(students_list[row]), end="")
        for col in range(num_subjects):
            print("{:<15.2f}".format(sum([grades_table[i][col] for i in range(num_students-1)])), end="")
    else:
        print("{:<15}".format(students_list[row]), end="")
        for col in range(num_subjects):
            print("{:<15.2f}".format(grades_table[row][col]), end="")
 #       print("{:<15}".format(letter_grades[row]))

# calculate the average grade for each subject
avg_grades = [sum([grades_table[row][col] for row in range(num_students-1)]) / (num_students-1) for col in range(num_subjects)]

# add the row of "----" to separate "Grand Totals"
print('------------------------------------------------------------------------')

# add the row of average grades
print("{:<15}".format('Average'), end="")
for avg_grade in avg_grades:
    print("{:<15.2f}".format(avg_grade), end="")
print("{:<15}".format(''))

# add the row of "----" to separate average grades from rest of table
print('------------------------------------------------------------------------')

# add the row of grand totals
print("{:<15}".format(students_list[num_students-1]), end="")
for col in range(num_subjects):
    print("{:<15.2f}".format(sum([grades_table[row][col] for row in range(num_students-1)])), end="")
print("{:<15}".format(''))

# add the rank column header
print("{:<105}".format('Ranking'))
print('------------------------------------------------------------------------')
print('               Ranks  As: 2   Bs: 5   Cs: 5     Ds: 2   Fs: 1 ')
print('===============================End of Report============================')
