
def display_menu():
    print("\nTo-Do List Menu:")
    print("1. Add a Task")
    print("2. View Tasks")
    print("3. Mark Task as Done")
    print("4. Exit")

def add_task(tasks):
    task = input("Enter the task: ")
    tasks.append({"task": task, "done": False})
    print(f"Task '{task}' added successfully!")

def view_tasks(tasks):
    if not tasks:
        print("Your to-do list is empty!")
    else:
        print("\nYour To-Do List:")
        for i, task in enumerate(tasks, 1):
            status = "Done" if task["done"] else "Not Done"
            print(f"{i}. {task['task']} [{status}]")

def mark_task_done(tasks):
    view_tasks(tasks)
    if tasks:
        try:
            task_number = int(input("\nEnter the task number to mark as done: "))
            if 1 <= task_number <= len(tasks):
                tasks[task_number - 1]["done"] = True
                print(f"Task '{tasks[task_number - 1]['task']}' marked as done!")
            else:
                print("Invalid task number. Please try again.")
        except ValueError:
            print("Please enter a valid number.")

def main():
    tasks = []  # List to store tasks
    while True:
        display_menu()
        try:
            choice = int(input("\nEnter your choice: "))
            if choice == 1:
                add_task(tasks)
            elif choice == 2:
                view_tasks(tasks)
            elif choice == 3:
                mark_task_done(tasks)
            elif choice == 4:
                print("Goodbye!")
                break
            else:
                print("Invalid choice. Please choose a number from 1 to 4.")
        except ValueError:
            print("Please enter a valid number.")


main()
