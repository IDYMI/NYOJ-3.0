export const m = {
  Tips: 'Tips',
  Warning: 'Warning',
  Successfully: 'Successfully',
  // /views/admin/Login.vue
  Welcome_to_Login_Admin: 'Welcome to Login Background Management System',
  Login: 'Login',
  Please_enter_username: 'Please enter username',
  Please_enter_password: 'Please enter password',
  Admin_Login_Success: 'Dear administrator, welcome back~',
  Please_check_your_username_or_password: 'Please check your username or password',

  // /views/admin/Home.vue
  Dashboard: 'Dashboard',
  Important: 'Important',
  General: 'General',
  User_Admin: 'Admin User',
  Auth_Admin: 'Auth Admin',
  Announcement_Admin: 'Announcement',
  System_Config: 'System Config',
  System_Switch: 'System Switch',
  File_Admin: 'File',
  Problem_Admin: 'Problem',
  Problem_List: 'Problem List',
  Create_Problem: 'Create Problem',
  Admin_Group_Apply_Problem: 'Group Problem Examine',
  Export_Import_Problem: 'Export | Import Problem',
  Training_Admin: 'Training',
  Training_List: 'Training List',
  Create_Training: 'Create Training',
  Admin_Category: 'Admin Category',
  Contest_Admin: 'Contest',
  Contest_List: 'Contest List',
  Create_Contest: 'Create Contest',
  Discussion: 'Discussion',
  Discussion_Admin: 'Admin Discussion',
  Home_Page: 'Home Page',
  Logout: 'Logout',
  Log_Out_Successfully: 'Log out successfully',

  // /views/admin/Dashboard.vue
  Last_Login: 'Last Login',
  Super_Admin: 'Super Admin',
  Admin: 'Normal Admin',
  All_Problem_Admin: 'Problem Admin',
  Total_Users: 'Total Users',
  Today_Submissions: 'Today Submissions',
  Recent_14_Days_Contests: 'Recent 14 Days Contests',
  Backend_System: 'Backend System',
  Server_Number: 'Server Number',
  Nacos_Status: 'Nacos Status',
  HTTPS_Status: 'HTTPS Status',
  Backend_Service: 'Backend Service',
  Name: 'Name',
  Host: 'Host',
  Port: 'Port',
  CPU_Core: 'CPU Core',
  CPU_Usage: 'CPU Usage',
  Mem_Usage: 'Mem Usage',
  Healthy: 'Healthy',
  Secure: 'Secure',
  Healthy_Status: 'Healthy',
  Unhealthy: 'Unhealthy',
  Judge_Server: 'Judge Server',

  // /views/admin/general/User.vue
  General_User: 'User',
  Created_Time: 'Created Time',
  Delete: 'Delete',
  OnlyAdmin: 'OnlyAdmin',
  User_Type: 'User Type',
  Normal: 'Normal',
  Disable: 'Disable',
  Edit_User: 'Edit User',
  Delete_User: 'Delete User',
  Import_User: 'Import User',
  Import_User_Tips1: 'The imported user data only supports user data in CSV format.',
  Import_User_Tips2: 'There are seven columns of data: username, password, email, realname and gender. The username and password cannot be empty, others can be enmpty, otherwise the data in this row may fail to be imported.',
  Import_User_Tips3: 'The first line does not need to write the seven column names ("username", "password", "email","realname","gender","nickname","school").',
  Import_User_Tips4: 'If the gender is male, please use "male" or "0", if the gender is female, please use "female" or "1". If it is not filled in, it is assumed to be "secrecy".',
  Import_User_Tips5: 'Please import the file saved as UTF-8 code, otherwise Chinese may be garbled.',
  Import_User_Tips6: 'There are four columns of data: realname, student_number, course, subject. The realname and student_number cannot be empty, others can be enmpty, otherwise the data in this row may fail to be imported.',
  Import_User_Tips7: 'The first line does not need to write the four column names ("realname", "student_number", "course","subject").',
  Import_User_Tips8: 'Add the content of the selected file to the end of the original list.',
  Choose_File: 'Choose File',
  Show_All: 'Show All',
  Password: 'Password',
  Upload_All: 'Upload All',
  Clear_All: 'Clear All',
  Generate_User: 'Generate User',
  Prefix: 'Prefix',
  Suffix: 'Suffix',
  Start_Number: 'Start Number',
  End_Number: 'End Number',
  Extra_Account: 'Extra Accounts Allowed to The Contest',
  Extra_Account_Tips: 'Please enter the username of the account allowed to enter to the contest, separated by spaces.',
  Password_Length: 'Password Length',
  Password_Custom: 'Password Custom',
  Account_Type: 'Account Type',
  True: 'True',
  False: 'False',
  Generate_and_Export: 'Generate & Export',
  The_usernames_will_be: 'The usernames will be',
  Set_New_PWD: 'Set PWD',
  General_New_Password: 'New PWD',
  Title_Color: 'Title Color',
  Title_Name: 'Title Name',
  The_end_number_cannot_be_less_than_the_start_number: 'The end number cannot be less than the start number',
  Please_select_6_to_25_characters_for_password_length: 'Please select 6 ~ 25 characters for password length',
  Start_Number_Required: 'The Start Number is required.',
  End_Number_Required: 'The End Number is required.',
  Password_Length_Checked: 'Password length must be numeric',
  Delete_User_Tips: 'Are you sure you want to delete this user? May be associated to delete the user created announcements, topics, competitions, etc.',
  The_number_of_users_selected_cannot_be_empty: 'The number of users selected cannot be empty',
  Error_Please_check_your_choice: 'Wrong, please check your choice.',
  Generate_User_Success: 'All users in the specified format have been created successfully, and the user table has been downloaded to your computer successfully!',
  Generate_Skipped_Reason: 'rows user data are filtered because it may be an empty row or a column(username or password) value is empty.',
  Generate_Skipped_Reason2: 'rows user data are filtered because it may be an empty row or a column(realname or student number) value is empty.',
  Upload_Users_Successfully: 'Upload Users Successfully',

  // /views/admin/general/Auth.vue
  General_Auth: 'Auth',
  Auth: 'Auth',
  Create_Auth: 'Create Auth',
  Edit_Auth: 'Edit Auth',
  Auth_Name: 'Name',
  Auth_Permission: 'Permission',
  Role_Auth: 'Role Auth',
  Role: 'Role',
  Auth_Id: 'Auth ID',
  Create_Role_Auth: 'Create Role Auth',
  Delete_Auth: 'Delete Auth',
  Delete_Auth_Tips: 'Are you sure you want to delete this auth?',

  // /views/admin/general/Announcement.vue
  General_Announcement: 'Announcement',
  Create: 'Create',
  Modified_Time: 'Modified Time',
  Edit_Announcement: 'Edit Announcement',
  Create_Announcement: 'Create Announcement',
  Delete_Announcement: 'Delete Announcement',
  Announcement_Title: 'Title',
  Announcement_Content: 'Content',
  Announcement_visible: 'Visible',
  Delete_Announcement_Tips: 'Are you sure you want to delete this announcement?',

  // /views/admin/general/SysNotice.vue
  SysNotice: 'Notification',
  Notice_Admin: 'Notification',
  Push_System_Notification_Every_Hour: 'Tips: Push System Notification Every Hour',
  Edit_Notice: 'Edit Notification',
  Create_Notice: 'Create Notification',
  Delete_Notice: 'Delete Notification',
  Notice_Title: 'Notification Title',
  Notice_Content: 'Notification Content',
  Notice_Push: 'Pushed',
  Notice_Recipient: 'Recipient',
  All_User: 'All User',
  Designated_User: 'Designated User',
  All_Admin: 'Admin',
  Delete_Notice_Tips: 'Are you sure you want to delete this notification?',

  // /views/admin/general/SystemConfig.vue
  Website_Config: 'Website Config',
  Base_Url: 'Base Url',
  Web_Name: 'Web Name',
  Short_Name: 'Short Name',
  Record_Name: 'Record Name',
  Record_Url: 'Record Url',
  Project_Name: 'Project Name',
  Project_Url: 'Project Url',
  Web_Desc: 'Web Desc',
  Allow_Register: 'Allow Register',
  Home_Rotation_Chart: 'Home Rotation Chart',
  SMTP_Config: 'SMTP Config',
  Email_BG: 'BG IMG',
  Email_BG_Desc: 'SMTP Template Background IMG Address',
  Send_Test_Email: 'Send Test Email',
  Email: 'Email',
  Mobile_Config: 'Mobile Config',
  Region_Id: 'Region ID',
  Domain: 'Domain',
  Access_Key_Id: 'Access Key Id',
  Secret: 'Secret',
  Sign_Name: 'Sign Name',
  Template_Code: 'Template Code',
  Send_Test_Message: 'Send Test Message',
  DataSource_Config: 'DataSource Config',
  Please_input_your_email: 'Please input your email',
  Related_Title: 'Title',
  Related_IconClass: 'Icon Class',

  // /views/admin/general/SysSwitch.vue
  Judge_Config: 'Judge Config',
  Open_Public_Judge: 'Open Public Judge',
  Open_Group_Judge: 'Open Group Judge',
  Open_Contest_Judge: 'Open Contest Judge',
  Non_Contest_Submission_Frequency: 'Non Contest Submission Frequency(s)',
  Discussion_Config: 'Discussion Config',
  Open_Public_Discussion: 'Open Public Discussion',
  Open_Group_Discussion: 'Open Group Discussion',
  Open_Contest_Comment: 'Open Contest Comment',
  Hide_Non_Contest_Submission_Code: 'Hide Non Master Station Contest Submission Code',
  Number_of_AC_required_for_ordinary_users_to_post: 'Users can create discussion (Required AC)',
  Number_of_posts_that_users_can_create_per_day: 'Number of Users can create discussion per day',
  Number_of_AC_required_for_Comment_of_ordinary_users: 'Users can comment (Required AC)',
  Group_Config: 'Group Config',
  Number_of_Groups_that_users_can_create_per_day: 'Number of Users can create group per day',
  Total_number_of_groups_that_ordinary_users_can_create: 'Total number of Users can create group',
  Number_of_AC_required_for_ordinary_users_to_create_group: 'Users can create group (Required AC)',
  Account_Config: 'Account Config',
  Account: 'Account',
  Super_Account: 'Super Account',
  Add_Account: 'Add Account',

  // /views/admin/problem/ProblemList.vue
  Contest_Problem_List: 'Contest Problem List',
  Display_ID: 'Display ID',
  Display_Title: 'Display Title',
  Original_Display: 'Original Display',
  Contest_Display: 'Contest Display',
  Add_Rmote_OJ_Problem: 'Add Remote OJ Problem',
  Add_From_Public_Problem: 'Add From Public Problem',
  ACM_Contest_Add_From_Public_Problem_Tips: 'ACM Contest: Only ACM type problem and Remote Judge problem can be imported',
  OI_Contest_Add_From_Public_Problem_Tips: 'OI Contest: Only OI type problem and Remote Judge problem can be imported',
  Auth: 'Auth',
  Modified_User: 'Modified User',
  All_Problem: 'All Problem',
  Public_Problem: 'Public Problem',
  Private_Problem: 'Private Problem',
  Contest_Problem: 'Contest Problem',
  Download_Testcase: 'Download Testcase',
  Add_Contest_Problem: 'Add Contest Problem',
  Remote_OJ: 'Remote OJ',
  Add: 'Add',
  Remove: 'Remove',
  Delete_Problem_Tips: 'Are you sure you want to delete this problem? Note: the relevant submission data for this issue will also be deleted.',
  Remove_Contest_Problem_Tips: 'Are you sure you want to remove the problem from the contest?',
  Add_Successfully: 'Add Successfully',
  Download_Testcase_Success: 'The testcase of this problem has been downloaded successfully!',
  Enter_The_Problem_Display_ID_in_the_Contest: 'Enter The Problem Display ID in the Contest',
  Problem_ID_is_required: 'Problem ID is required',
  The_Problem_Display_ID_in_the_Contest_is_required: 'The Problem Display ID in the Contest is required',
  Balloon_Color: 'Balloon Color',
  Update_Balloon_Color_Successfully: 'Update the Balloon color of the problem successfully!',

  // /views/admin/problem/Problem.vue
  Problem_Display_ID: 'Problem Display ID',
  Title: 'Title',
  Contest_Display_Title: 'Contest Display Title',
  Contest_Display_ID: 'Contest Display ID',
  Description: 'Description',
  Input_Description: 'Input Description',
  Output_Description: 'Output Description',
  Time_Limit: 'Time Limit',
  Memory_Limit: 'Memory Limit',
  Stack_Limit: 'Stack Limit',
  Code_Shareable: 'Code Shareable',
  Languages: 'Languages',
  Problem_Examples: 'Problem Examples',
  Problem_Examples_Desc: 'Problem Examples: please do not have more than 2 problem examples. Problem examples are not included in the testcase.',
  Problem_Example: 'Example',
  Example_Input: 'Example Input',
  Example_Output: 'Example Output',
  Add_Example: 'Add Example',
  Judge_Mode: 'Judge Mode',
  General_Judge: 'General Judge',
  Special_Judge: 'Special Judge',
  Interactive_Judge: 'Interactive Judge',
  Special_Judge_Code: 'Special Judge Program Code',
  Interactive_Judge_Code: 'Interactive Judge Program Code',
  General_Judge_Mode_Tips: "General Judge: the contestant program reads the problem standard input file, executes the code logic to obtain the contestant's output, and compares the contents of the problem standard output file to obtain the problem judgment result",
  Special_Judge_Mode_Tips: 'Special Judge: the output results required by the problem may not be unique, and different results are allowed. Therefore, a special program is needed to read standard output, player output and standard input, and compare them to obtain the final judgment result',
  Interactive_Judge_Mode_Tips: 'Interactive Judge: the standard output of the interactive program is written to the standard input of the player program through the interactive channel, and the standard output of the player program is written to the standard input of the interactive program through the interactive channel. Both need to flush the output buffer',
  SPJ_Language: 'SPJ Program Language',
  Interactive_Language: 'Interactive Program Langugae',
  Compile: 'Compile',
  Compiled_Successfully: 'Compiled Successfully',
  Code_Template: 'Code Template',
  Code_template_of_the_language_cannot_be_empty: 'Code template of the language cannot be empty!',
  Type: 'Type',
  Read_Write_Mode: 'Read-Write Mode',
  Standard_IO: 'Standard IO',
  File_IO: 'File IO',
  Input_File_Name: 'Input File Name',
  Output_File_Name: 'Output File Name',
  When_the_read_write_mode_is_File_IO_the_input_file_name_or_output_file_name_cannot_be_empty: 'When the read-write mode is File IO, the input file name or output file name cannot be empty',
  Judge_Samples: 'Judge Samples',
  Problem_Sample: 'Sample',
  Sample_Input: 'Sample Input',
  Sample_Output: 'Sample Output',
  Sample_Input_File: 'Input File',
  Sample_Output_File: 'Output File',
  Sample_Group_Num: 'Subtask Number',
  Sample_Tips: 'Sample: the data source of the judger to test the submission.',
  Add_Sample: 'Add Sample',
  Use_Upload_File: 'Use Upload File',
  Use_Manual_Input: 'Use Manual Input',
  Hint: 'Hint',
  Source: 'Source',
  Auto_Remove_the_Blank_at_the_End_of_Code: 'Automatically Remove Whitespace at The End of Each Line of Code',
  Publish_the_Judging_Result_of_Test_Data: 'Publish the Judging Result of Test Data',
  Edit_Problem: 'Edit Problem',
  Create_Problem: 'Create Problem',
  Change_Judge_Mode: 'Note: switching the judgment mode may change the evaluation logic!',
  Add_Tag_Error: 'The tag has been added, please do not add it repeatedly!',

  Upload_Testcase_Successfully: 'Upload Testcase Successfully',
  Upload_Testcase_Failed: 'Upload Testcase Failed',
  is_required: 'is required!',
  Score_must_be_greater_than_or_equal_to_0: 'Score must be greater than or equal to 0!',
  Score_must_be_an_integer: 'Score must be an integer!',
  Spj_Or_Interactive_Code: 'Spj Or Interactive Code',
  Spj_Or_Interactive_Code_not_Compile_Success: 'Spj Or Interactive Code was not compiled successfully, please compile again!',
  Judge_Extra_File: 'Judge Extra File',
  Judge_Extra_File_Tips1: '1. User Program: Provide additional library files for user program',
  Judge_Extra_File_Tips2: '2. Special Or Interactive Program: Provide additional library files for special or interactive programs',
  User_Program: 'User Program',
  SPJ_Or_Interactive_Program: 'Special Or Interactive Program',
  Non_Default_Judge_Case_Mode_And_Group_Num_IS_NULL: 'The number of subtask groups of evaluation data cannot be empty!',
  OI_Judge_Case_Default_Mode: 'Sum of scores of all samples',
  ACM_Judge_Case_Default_Mode: 'Judge all test cases',
  Judge_Case_Subtask_Lowest_Mode: 'Subtask (Lowest Score)',
  Judge_Case_Subtask_Average_Mode: 'Subtasks (Average Score)',
  Judge_Case_Ergodic_Without_Error_Mode: 'Stop judge when error',

  // /views/admin/problem/Problem.vue
  Selection_Options: 'Selection Options',
  Filling_Count: 'Filling Count',
  Selection_Option: 'Option',
  Selection_View: 'View',
  Add_Selection: 'Add Selection',
  Problem_Answer: 'Problem Answer',
  Problem_Sample2: 'Answer',
  Problem_Answer_Output: 'Answer Output',
  Add_Answer: 'Add Answer',
  Examples_Desc: 'Upload File: Compress all data files in the folder into a zip-type compressed package',
  Answer_Examples_Desc1: 'Answer format: For multiple-choice questions, the answer is a series of answers separated by line breaks in alphabetical order (e.g., selecting A and B would be input as `A` `\\n` `B`)',
  Answer_Examples_Desc2: 'Answer format: For fill-in-the-blank questions, the answer is a series of answers separated by line breaks (e.g., selecting A and B would be input as `A` `\\n` `B`)',
  Answer_Examples_Desc3: 'Answer format: For true or false questions, the answer is either YES or NO, usually just one word!',

  // /views/admin/problem/Tag.vue
  Admin_Tag: 'Admin Tag',
  Add_Tag: 'Add Tag',
  Update_Tag: 'Update Tag',
  To_Add: 'Add',
  To_Update: 'Update',

  Tag_Name: 'Tag Name',
  Tag_Color: 'Tag Color',
  Tag_Attribution: 'Tag Attribution',
  Delete_Tag_Tips: 'Are you sure you want to delete this tag?',
  Tag_Tips: 'Note: By default, there is no tag classification column, and all tags are [Unclassified]. Please click [Add Tag Classification] in the upper left corner to add tag classification, and then click tag modification #Tag Classification# to classify.',
  Tag_Classification: 'Tag Classification',
  Add_Tag_Classification: 'Add Tag Classification',
  Update_Tag_Classification: 'Update Tag Classification',
  Tag_Classification_Name: 'Tag Classification Name',
  Tag_Classification_Rank: 'Tag Classification Rank',
  Tag_Classification_Attribution: 'Tag Classification Attribution',
  Delete_Tag_Classification_Tips: 'Are you sure you want to delete this tag classification? Prompt: This operation will attribute the labels under this classification to unclassified!',
  Unclassified: 'Unclassified',

  // /views/amdin/problem/GroupProblemList.vue
  Search: 'Search',
  Enter_Group_ID: 'Enter Group ID',
  Agreed: 'Agreed',
  Examine: 'Examine',

  // /views/admin/training/TrainingList.vue
  All_Traning: 'All Traning',
  Update_Time: 'Update Time',
  Order_Number: 'Order Number',
  View_Training_Problem_List: 'View Training Problem List',
  Delete_Training_Tips: 'This operation will delete the training and its submission, rank record and other data. Do you want to continue?',

  // /views/admin/training/Training.vue
  Training_rank: 'Training Sort Number (Ascending Sort)',
  Training_Title: 'Training Title',
  Training_Description: 'Training Description',
  Training_Auth: 'Training Auth',
  Training_Category: 'Training Category',
  Public_Training: 'Public Training',
  Private_Training: 'Private Training',
  Training_Password: 'Training Password',
  Edit_Training: 'Edit Training',
  Redirect_To_Category: 'The category list of current training is empty. Please go to create category first!',
  Redirect: 'Redirect',

  // /views/admin/training/TrainingProblemList.vue
  Training_Problem_List: 'Training Problem List',
  Add_Training_Problem: 'Add Training Problem',
  Remove_Training_Problem_Tips: 'Are you sure you want to remove the problem from the training?',
  Training_Problem_Rank: 'Title Display Order(Ascending)',

  // /views/admin/training/Category.vue
  Add_Category: 'Add Category',
  Update_Category: 'Update Category',
  To_Add: 'Add',
  To_Update: 'Update',
  Category_Name: 'Category Name',
  Category_Color: 'Category Color',
  Delete_Category_Tips: 'Are you sure you want to delete this category?',

  // /views/admin/problem/ImportAndExport.vue
  Export_Problem: 'Export Problem',
  Export: 'Export',
  Import_Problem: 'Import Problem',
  Import_QDUOJ_Problem: 'Import QDUOJ Problem',
  Import_FPS_Problem: 'Import FPS Problem',
  Import_Hydro_Problem: 'Import Hydro Problem',
  Export_Problem_NULL_Tips: 'The problem selected for export cannot be empty',
  Upload_Problem_Succeeded: 'Upload Problem Succeeded',
  Upload_Problem_Failed: 'Upload Problem Failed',

  // /views/admin/contest/ContestList.vue
  All_Contest: 'All Contest',
  Start_Time: 'Start Time',
  End_Time: 'End Time',
  Creator: 'Creator',
  Visible: 'Visible',
  Info: 'Info',
  View_Contest_Problem_List: 'View Contest Problem List',
  View_Contest_Announcement_List: 'View Contest Announcement List',
  Download_Contest_AC_Submission: 'Download Contest AC Submissions',
  Exclude_admin_submissions: 'Exclude admin submissions',
  SplitType_User: 'Split folders by username',
  SplitType_Problem: 'Split folders by problem id',
  Delete_Contest_Tips: 'This operation will delete the contest and its submission, discussion, announcement, record and other data. Do you want to continue?',

  // /views/admin/contest/Contest.vue
  Contest_Title: 'Contest Title',
  Contest_Description: 'Contest Description',
  Contest_Start_Time: 'Start Time',
  Contest_End_Time: 'End Time',
  Contest_Duration: 'Contest Duration',
  Contest_Rule_Type: 'Contest Rule Type',
  Seal_Time_Rank: 'Seal Time Rank',
  Real_Time_Rank: 'Real Time Rank',
  Seal_Rank_Time: 'Seal Rank Time',
  Timeliness_Of_Rank: 'Timeliness Of Rank',
  Contest_Auth: 'Contest Auth',
  Contest_Password: 'Contest Password',
  OI_Rank_Score_Type: 'OI RANK Score Type',
  OI_Rank_Score_Type_Recent: 'Use Recent Score',
  OI_Rank_Score_Type_Highest: 'Use Highest Score',
  Contest_Seal_Half_Hour: 'Half an hour',
  Contest_Seal_An_Hour: 'An hour',
  Contest_Seal_All_Hour: 'All hours',
  Auto_Real_Rank: 'Auto Turn Into Real Time Rank',
  Real_Rank_After_Contest: 'Real Time Rank After Contest',
  Seal_Rank_After_Contest: 'Seal Rank After Contest',
  Edit_Contest: 'Edit Contest',
  Create_Contest: 'Create Contest',
  Contest_Duration_Check: 'The duration of the contest cannot be less than or equal to zero!',
  Contets_Time_Check: 'The start time should be earlier than the end time!',
  Print_Func: 'Print Function',
  Open: 'Open',
  Not_Support_Print: 'Not Support Print',
  Support_Offline_Print: 'Support Offline Print',
  BoxFile_Func: 'File Box',
  Not_Support_BoxFile: 'Not Support File Box',
  Support_BoxFile: 'Support File Box',
  Edit_BoxFile: 'Edit File Box',
  Add_Star_User_Error: 'Please do not add existing star user repeatedly!',
  Star_User_UserName: 'Star User (Please use login username)',
  Rank_Show_Name: 'The Name Showed in The Rank',
  Show_Username: 'Username',
  Show_Nickname: 'Nickname',
  Show_Realname: 'Real name',
  Account_Limit: 'Account Limit (Login Username)',
  The_allowed_account_will_be: 'The allowed username will be ',
  Contest_Award: 'Contest Award',
  Contest_Award_Null: 'Do not set',
  Contest_Award_Set_Number: 'Set the number',
  Contest_Award_Set_Proportion: 'Set the proportion',
  Contest_Award_Priority: 'Priority',
  Contest_Award_Name: 'Award Name',
  Contest_Award_Background: 'Award Background',
  Contest_Award_Color: 'Award Color',
  Contest_Award_Show: 'Award Style Display',
  Contest_Award_Number: 'Number',
  Contest_Award_Proportion: 'Proportion',
  Allow_Submission_After_The_Contest_Ends: 'Allow Submission after the contest ends',
  Synchronous: 'Synchronous',
  Synchronous_School: 'Synchronous School',
  Synchronous_Link: 'Synchronous Link',
  Synchronous_Username: 'Synchronous Username (used for web scraping)',
  Synchronous_Password: 'Synchronous Password (used for web scraping)',
  Sign_Start_Time: 'Sign Start Time',
  Sign_End_Time: 'Sign End Time',
  Max_Participants: 'Max Participants (Up To Three)',
  Sign_Duration: 'Sign Duration',
  Sign_Duration_Check: 'The duration of the sign cannot be less than or equal to zero!',
  Sign_EndTime_Check: 'The EndTime of the sign cannot after the EndTime of the contest',

  // /views/admin/discussion/Discussion.vue
  Discussion_ID: 'Discussion ID',
  Top: 'Top',
  Discussion_Report: 'Discussion Report',
  Reporter: 'Reporter',
  Report_Time: 'Report Time',
  View_Report_content: 'View Report Content',
  View_Discussion: 'View Discussion Detail',
  Content: 'Content',
  Report_Content: 'Report Content',
  The_number_of_discussions_selected_cannot_be_empty: 'The number of discussions selected cannot be empty',

  // components/admin/AddExtraFile.vue
  Delete_Extra_File_Tips: 'Are you sure you want to delete this extra file?',
  File_Name: 'File Name',
  File_Content: 'File Content',

  // /views/admin/general/SystemConfig.vue
  Url: 'Add Url',
  Hint2: 'Add Hint',
  Edit_Home_Rotation_Chart: 'Edit Home Rotation Chart',
  Edit_Box_File: 'Edit Box FileName',
  Box_File: 'Offered File',

  // Validator
  The_title_role: 'is not in compliance (cannot contain $).',
  Title_Required: 'The Title is required',
  Input_Description_Required: 'Input Description is required',
  Output_Description_Required: 'Output Description is required',
  Rule_NotDeal: 'Not Deal Rules',
  The_username_role: 'The username is not in compliance (cannot contain $).',

  // ExaminationRoom
  ExaminationRoom_Admin: 'ExaminationRoom',
  Create_ExaminationRoom: 'Create ExaminationRoom',
  Assign_ExaminationSeat: 'Assign ExaminationSeat',
  Edit_ExaminationRoom: 'Edit ExaminationRoom',
  ExaminationRoom_List: 'ExaminationRoom List',
  Assign_ExaminationRoom_List: 'Assign ExaminationRoom List',
  To_Admin_Contest: 'To Admin Contest',
  View_Assign_ExaminationSeat: 'View Assign ExaminationRoom List',
  To_Assign_ExaminationSeat: 'To Assign ExaminationSeat',
  Assign_Success: 'Assign Success',
  Assign_Failed: 'Assign Failed',

  // ExaminationSeat
  Add_Place: 'Add Place',
  Eid_List: 'Eid List',
  Retroflex: 'Retroflex',
  Random: 'Random',
  Sorted: 'Sorted Id',
  Spaced: 'Spaced',
  StudentInfo: 'Student Info',
  Subject: 'Subject',
};
