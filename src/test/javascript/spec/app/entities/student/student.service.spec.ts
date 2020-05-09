/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent, Student } from 'app/shared/model/student.model';

describe('Service Tests', () => {
  describe('Student Service', () => {
    let injector: TestBed;
    let service: StudentService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudent;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StudentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Student(
        0,
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Student', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Student(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Student', async () => {
        const returnedFromService = Object.assign(
          {
            stuId: 1,
            gender: true,
            stuClass: 'BBBBBB',
            stuDepartment: 'BBBBBB',
            stuProfession: 'BBBBBB',
            phone: 'BBBBBB',
            privince: 'BBBBBB',
            privinceCode: 'BBBBBB',
            city: 'BBBBBB',
            cityCode: 'BBBBBB',
            country: 'BBBBBB',
            countryCode: 'BBBBBB',
            address: 'BBBBBB',
            hobby: 'BBBBBB',
            skill: 'BBBBBB',
            selfEvaluation: 'BBBBBB',
            skin: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Student', async () => {
        const returnedFromService = Object.assign(
          {
            stuId: 1,
            gender: true,
            stuClass: 'BBBBBB',
            stuDepartment: 'BBBBBB',
            stuProfession: 'BBBBBB',
            phone: 'BBBBBB',
            privince: 'BBBBBB',
            privinceCode: 'BBBBBB',
            city: 'BBBBBB',
            cityCode: 'BBBBBB',
            country: 'BBBBBB',
            countryCode: 'BBBBBB',
            address: 'BBBBBB',
            hobby: 'BBBBBB',
            skill: 'BBBBBB',
            selfEvaluation: 'BBBBBB',
            skin: 'BBBBBB',
            createdTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Student', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
