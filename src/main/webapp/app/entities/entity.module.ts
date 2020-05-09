import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        loadChildren: './student/student.module#IntershipSystemStudentModule'
      },
      {
        path: 'teacher',
        loadChildren: './teacher/teacher.module#IntershipSystemTeacherModule'
      },
      {
        path: 'stu-tea',
        loadChildren: './stu-tea/stu-tea.module#IntershipSystemStuTeaModule'
      },
      {
        path: 'intership',
        loadChildren: './intership/intership.module#IntershipSystemIntershipModule'
      },
      {
        path: 'attendance-management',
        loadChildren: './attendance-management/attendance-management.module#IntershipSystemAttendanceManagementModule'
      },
      {
        path: 'leave',
        loadChildren: './leave/leave.module#IntershipSystemLeaveModule'
      },
      {
        path: 'absence-record',
        loadChildren: './absence-record/absence-record.module#IntershipSystemAbsenceRecordModule'
      },
      {
        path: 'job-change-records',
        loadChildren: './job-change-records/job-change-records.module#IntershipSystemJobChangeRecordsModule'
      },
      {
        path: 'internship-report',
        loadChildren: './internship-report/internship-report.module#IntershipSystemInternshipReportModule'
      },
      {
        path: 'internship-task',
        loadChildren: './internship-task/internship-task.module#IntershipSystemInternshipTaskModule'
      },
      {
        path: 'graduation-project',
        loadChildren: './graduation-project/graduation-project.module#IntershipSystemGraduationProjectModule'
      },
      {
        path: 'sharing-center',
        loadChildren: './sharing-center/sharing-center.module#IntershipSystemSharingCenterModule'
      },
      {
        path: 'attendance-record',
        loadChildren: './attendance-record/attendance-record.module#IntershipSystemAttendanceRecordModule'
      },
      {
        path: 'alarm-event',
        loadChildren: './alarm-event/alarm-event.module#IntershipSystemAlarmEventModule'
      },
      {
        path: 'certificate',
        loadChildren: './certificate/certificate.module#IntershipSystemCertificateModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemEntityModule {}
