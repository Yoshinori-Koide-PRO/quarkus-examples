import { DefaultService } from './quarkus-client';
import { Person } from './quarkus-client/model/models'
import { Component , OnInit} from '@angular/core';
import { traceable } from 'jaeger-tracer-decorator';

@traceable()
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'myQuarkusAngular';

  user : Person;

  constructor(private apiGateway: DefaultService) { }

  @traceable()
  async ngOnInit() {
    this.apiGateway.personIdGet(1,'body').subscribe(p => {
      console.log(`person::${JSON.stringify(p, null, " ")}`);
      this.user = p;});
  }

}
