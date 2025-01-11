import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-room-availability',
  templateUrl: './room-availability.component.html',
  styleUrls: ['./room-availability.component.css']
})
export class RoomAvailabilityComponent implements OnInit {
  // Bar Chart Data for Room Types
  public barChartData: any[] = [
    {
      label: 'Room Types',
      data: [0, 0], // Initialize with default values
      backgroundColor: ['#36A2EB', '#FF6384'] // Colors for Single and Double
    }
  ];
  public barChartLabels: string[] = ['Single', 'Double']; // Labels for the bar chart
  public barChartType: 'bar' = 'bar'; // Bar chart type
  public barChartOptions: any = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
        max: 100,
        title: {
          display: true,
          text: 'Percentage (%)'
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
        text: 'Room Type Distribution'
      }
    }
  };

  // Pie Chart Data for Availability
  public pieChartData: any[] = [
    {
      label: 'Room Availability',
      data: [0, 0], // Initialize with default values
      backgroundColor: ['#4CAF50', '#FF5252'] // Colors for Available and Unavailable
    }
  ];
  public pieChartLabels: string[] = ['Available', 'Unavailable']; // Labels for the pie chart
  public pieChartType: 'pie' = 'pie'; // Pie chart type
  public pieChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      },
      title: {
        display: true,
        text: 'Room Availability Percentage'
      }
    }
  };

  token = this.authService.getToken();
  private apiUrl = 'http://localhost:8090/admin/statistics';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.fetchRoomStatistics();
  }

  fetchRoomStatistics(): void {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    this.http.get<any>(this.apiUrl, { headers }).subscribe(
      (data) => {
        console.log('Data from API:', data);

        // Update Bar Chart (Room Types)
        this.barChartLabels = ['Single', 'Double'];
        this.barChartData = [
          {
            label: 'Room Types',
            data: [data.SINGLE ?? 0, data.DOUBLE ?? 0],
            backgroundColor: ['#36A2EB', '#FF6384']
          }
        ];

        // Update Pie Chart (Availability)
        this.pieChartLabels = ['Available', 'Unavailable'];
        this.pieChartData = [
          {
            label: 'Room Availability',
            data: [data.AVAILABLE ?? 0, data.UNAVAILABLE ?? 0],
            backgroundColor: ['#4CAF50', '#FF5252']
          }
        ];

        console.log('Bar Chart Data:', this.barChartData);
        console.log('Pie Chart Data:', this.pieChartData);
      },
      (error) => {
        console.error('Error fetching room statistics:', error);
      }
    );
  }
}