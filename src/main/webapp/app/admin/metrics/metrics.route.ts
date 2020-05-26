import {Route} from '@angular/router';

import {JhiMetricsMonitoringComponent} from './metrics.component';
import {NavbarComponent} from "app/layouts";

export const metricsRoute: Route = {
  path: 'jhi-metrics',
  component: JhiMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title'
  }
  //
  // path: '',
  // component: JhiMetricsMonitoringComponent,
  // outlet: 'metrics'
};
