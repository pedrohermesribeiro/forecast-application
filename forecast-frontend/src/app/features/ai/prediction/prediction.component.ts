import { Component, Input, AfterViewInit, ElementRef, ViewChild, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostListener } from '@angular/core';
import { registerables } from 'chart.js';
import Chart from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';

Chart.register(...registerables, ChartDataLabels);

@Component({
  selector: 'app-prediction',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prediction.component.html',
  styleUrl: './prediction.component.css'
})
export class PredictionComponent implements AfterViewInit, OnDestroy {
  @Input() previsaoData: { mes: string; vendas: number; taxa: number }[] = [];

  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;
  private chart?: Chart;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.chart?.resize();
  }

  ngAfterViewInit() {
    if (this.previsaoData?.length > 0 && this.chartCanvas?.nativeElement) {
      this.createOrUpdateChart();
    }
  }

  private createOrUpdateChart() {
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: this.previsaoData.map(item => item.mes),
        datasets: [{
          label: 'Vendas Previstas (unidades)',
          data: this.previsaoData.map(item => item.vendas),
          backgroundColor: 'rgba(54, 162, 235, 0.65)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          borderRadius: 4,
          yAxisID: 'y',
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        layout: { padding: { top: 40 } },
        scales: {
          y: {
            beginAtZero: true,
            title: { display: true, text: 'Vendas (unidades)', font: { size: 14 } },
            ticks: {
              callback: (value) => (Number(value) / 1000000).toFixed(1) + 'M'
            }
          },
          x: {
            title: { display: true, text: 'Mês', font: { size: 14 } },
            ticks: { font: { size: 13 }, maxRotation: 0, minRotation: 0 }
          }
        },
        plugins: {
          legend: { display: true, position: 'top', labels: { font: { size: 14 }, boxWidth: 20 } },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context) => {
                const value = context.raw as number;
                return `Vendas: ${(value / 1000000).toFixed(1)} milhões`;
              }
            }
          },
          datalabels: {
            anchor: 'end',
            align: 'top',
            offset: 6,
            color: '#333',
            backgroundColor: 'rgba(255,255,255,0.75)',
            borderRadius: 4,
            padding: 6,
            font: { weight: 'bold', size: 13 },
            formatter: (value: number) => {
              if (value >= 1000000) return (value / 1000000).toFixed(1) + 'M';
              return new Intl.NumberFormat('pt-BR').format(value);
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });
  }

  ngOnDestroy() {
    this.chart?.destroy();
  }

  // Helper para formatar a taxa visualmente (ex: +12% ou −8%)
  formatTaxa(taxa: number): string {
    const sinal = taxa >= 0 ? '+' : '−';
    const valorAbs = Math.abs(taxa);
    return `${sinal}${valorAbs}%`;
  }

  // Classe para cor da taxa (verde positivo, vermelho negativo)
  getTaxaClass(taxa: number): string {
    return taxa >= 0 ? 'taxa-positiva' : 'taxa-negativa';
  }
}
