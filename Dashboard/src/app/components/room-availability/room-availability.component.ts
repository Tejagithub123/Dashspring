import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-room-availability',
  templateUrl: './room-availability.component.html',
  styleUrls: ['./room-availability.component.css']
})
export class RoomAvailabilityComponent implements OnInit {
  // Chart data and options
  public barChartData: any[] = [
    {
      label: 'Rooms by single double',
      data: [0, 0], // Initialize with default values
      backgroundColor: ['#36A2EB', '#FF6384']
    }
  ];
  public barChartLabels: string[] = ['Single', 'Double']; // Initialize with default labels
  public barChartType: 'bar' | 'line' | 'pie' | 'doughnut' | 'radar' | 'polarArea' = 'bar';
  public barChartOptions: any = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
        max: 100,
        title: {
          display: true,
          text: 'Availability (%)'
        }
      },
      x: {
        title: {
          display: true,
          text: 'Room Type'
        }
      }
    },
    plugins: {
      legend: {
        display: false
      },
      title: {
        display: true,
        text: 'Room Availability Percentage'
      }
    }
  };

  token = this.authService.getToken();
  private apiUrl = 'http://localhost:8090/personnel/statistics';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.fetchRoomAvailability();
  }

  fetchRoomAvailability(): void {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    this.http.get<any>(this.apiUrl, { headers }).subscribe(
      (data) => {
        console.log('Data from API:', data);

        // Update the chart with the response data
        this.barChartLabels = ['Single', 'Double'];
        this.barChartData = [
          {
            label: 'Room Availability',
            data: [data.SINGLE ?? 0, data.DOUBLE ?? 0],
            backgroundColor: ['#36A2EB', '#FF6384']
          }
        ];

        console.log('Chart Labels:', this.barChartLabels);
        console.log('Chart Data:', this.barChartData);
      },
      (error) => {
        console.error('Error fetching room availability data:', error);
      }
    );
  }
}