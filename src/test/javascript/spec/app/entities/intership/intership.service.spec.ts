/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IntershipService } from 'app/entities/intership/intership.service';
import { IIntership, Intership } from 'app/shared/model/intership.model';

describe('Service Tests', () => {
  describe('Intership Service', () => {
    let injector: TestBed;
    let service: IntershipService;
    let httpMock: HttpTestingController;
    let elemDefault: IIntership;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(IntershipService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Intership(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0,
        0,
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
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            beginIntership: currentDate.format(DATE_TIME_FORMAT),
            endIntership: currentDate.format(DATE_TIME_FORMAT),
            createTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Intership', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            beginIntership: currentDate.format(DATE_TIME_FORMAT),
            endIntership: currentDate.format(DATE_TIME_FORMAT),
            createTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            beginIntership: currentDate,
            endIntership: currentDate,
            createTime: currentDate,
            modifyTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Intership(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Intership', async () => {
        const returnedFromService = Object.assign(
          {
            batchName: 'BBBBBB',
            practiceType: 'BBBBBB',
            course: 'BBBBBB',
            beginIntership: currentDate.format(DATE_TIME_FORMAT),
            endIntership: currentDate.format(DATE_TIME_FORMAT),
            tutorScore: 1,
            masterScore: 1,
            intershipScore: 1,
            companyCreditCode: 'BBBBBB',
            companyName: 'BBBBBB',
            internship: 'BBBBBB',
            companyContactName: 'BBBBBB',
            companyContactJob: 'BBBBBB',
            companyContactPhone: 'BBBBBB',
            masterContactName: 'BBBBBB',
            masterContactSkill: 'BBBBBB',
            masterContactPhone: 'BBBBBB',
            urgentContactName: 'BBBBBB',
            urgentContactPhone: 'BBBBBB',
            urgentContactAddress: 'BBBBBB',
            accommodationType: 'BBBBBB',
            accommodationAddress: 'BBBBBB',
            companyAddress: 'BBBBBB',
            companyDetailAddress: 'BBBBBB',
            companyNature: 'BBBBBB',
            scale: 'BBBBBB',
            industry: 'BBBBBB',
            companyProfile: 'BBBBBB',
            isInsurance: 'BBBBBB',
            insuranceCompanyAndPolicyNumber: 'BBBBBB',
            isInternshipAgreement: true,
            annex: 'BBBBBB',
            createTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            beginIntership: currentDate,
            endIntership: currentDate,
            createTime: currentDate,
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

      it('should return a list of Intership', async () => {
        const returnedFromService = Object.assign(
          {
            batchName: 'BBBBBB',
            practiceType: 'BBBBBB',
            course: 'BBBBBB',
            beginIntership: currentDate.format(DATE_TIME_FORMAT),
            endIntership: currentDate.format(DATE_TIME_FORMAT),
            tutorScore: 1,
            masterScore: 1,
            intershipScore: 1,
            companyCreditCode: 'BBBBBB',
            companyName: 'BBBBBB',
            internship: 'BBBBBB',
            companyContactName: 'BBBBBB',
            companyContactJob: 'BBBBBB',
            companyContactPhone: 'BBBBBB',
            masterContactName: 'BBBBBB',
            masterContactSkill: 'BBBBBB',
            masterContactPhone: 'BBBBBB',
            urgentContactName: 'BBBBBB',
            urgentContactPhone: 'BBBBBB',
            urgentContactAddress: 'BBBBBB',
            accommodationType: 'BBBBBB',
            accommodationAddress: 'BBBBBB',
            companyAddress: 'BBBBBB',
            companyDetailAddress: 'BBBBBB',
            companyNature: 'BBBBBB',
            scale: 'BBBBBB',
            industry: 'BBBBBB',
            companyProfile: 'BBBBBB',
            isInsurance: 'BBBBBB',
            insuranceCompanyAndPolicyNumber: 'BBBBBB',
            isInternshipAgreement: true,
            annex: 'BBBBBB',
            createTime: currentDate.format(DATE_TIME_FORMAT),
            modifyTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            beginIntership: currentDate,
            endIntership: currentDate,
            createTime: currentDate,
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

      it('should delete a Intership', async () => {
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
