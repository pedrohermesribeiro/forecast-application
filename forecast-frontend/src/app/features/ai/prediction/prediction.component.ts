import { Component, Input, AfterViewInit, ElementRef, ViewChild, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostListener } from '@angular/core';
import { registerables } from 'chart.js';
import Chart from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';


// Registrar tudo uma única vez (ideal fora do componente, mas aqui funciona)
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
  onResize(event: Event) {
    this.chart?.resize();
  }

  ngAfterViewInit() {
    console.log('[PredictionComponent] ngAfterViewInit chamado');
    console.log('[PredictionComponent] Dados recebidos:', this.previsaoData);

    // Só cria se tiver dados e o canvas existir
    if (this.previsaoData?.length > 0 && this.chartCanvas?.nativeElement) {
      this.createOrUpdateChart();
    }
  }

  private createOrUpdateChart() {
    // Destroi se já existir (evita duplicatas ou memory leak)
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
          borderRadius: 4,           // cantos arredondados (opcional, fica bonito)
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        layout: {
          padding: {
            top: 30     // espaço extra no topo para os rótulos não cortarem
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Vendas (unidades)',
              font: { size: 14 }
            },
            ticks: {
              callback: (value) => (Number(value))
            }
          },
          x: {
            title: {
              display: true,
              text: 'Mês',
              font: { size: 14 }
            },
            ticks: {
              font: { size: 13 },
              maxRotation: 0,
              minRotation: 0
            }
          }
        },
        plugins: {
          legend: {
            display: true,
            position: 'top',
            labels: { font: { size: 14 }, boxWidth: 20 }
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context) => {
                const value = context.raw as number;
                return `Vendas: ${(value)}`;
              }
            }
          },
          // ── RÓTULOS EM CIMA DAS BARRAS ──
          datalabels: {
            anchor: 'end',           // fixa no topo da barra
            align: 'top',            // alinhado acima
            offset: 6,               // distância da barra (ajuste se necessário)
            color: '#333',
            backgroundColor: 'rgba(255,255,255,0.7)',  // fundo semi-transparente (opcional)
            borderRadius: 4,
            padding: 6,
            font: {
              weight: 'bold',
              size: 13
            },
            formatter: (value: number) => {
              // Formata como na tabela: 5.800.000 → 5.800.000 ou 5,8M
              if (value >= 1000000) {
                return (value / 1000000).toFixed(1) + 'M';
              }
              return new Intl.NumberFormat('pt-BR', {
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
              }).format(value);
            }
          }
        }
      },
      plugins: [ChartDataLabels]   // importante: ativa o plugin
    });

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: this.previsaoData.map(item => item.mes),
        datasets: [{
          label: 'Vendas Previstas (unidades)',
          data: this.previsaoData.map(item => item.taxa),
          backgroundColor: 'rgba(54, 162, 235, 0.65)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          borderRadius: 4,           // cantos arredondados (opcional, fica bonito)
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        layout: {
          padding: {
            top: 30     // espaço extra no topo para os rótulos não cortarem
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Vendas (unidades)',
              font: { size: 14 }
            },
            ticks: {
              callback: (value) => (Number(value))
            }
          },
          x: {
            title: {
              display: true,
              text: 'Mês',
              font: { size: 14 }
            },
            ticks: {
              font: { size: 13 },
              maxRotation: 0,
              minRotation: 0
            }
          }
        },
        plugins: {
          legend: {
            display: true,
            position: 'top',
            labels: { font: { size: 14 }, boxWidth: 20 }
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context) => {
                const value = context.raw as number;
                return `Vendas: ${(value)}`;
              }
            }
          },
          // ── RÓTULOS EM CIMA DAS BARRAS ──
          datalabels: {
            anchor: 'end',           // fixa no topo da barra
            align: 'top',            // alinhado acima
            offset: 6,               // distância da barra (ajuste se necessário)
            color: '#333',
            backgroundColor: 'rgba(255,255,255,0.7)',  // fundo semi-transparente (opcional)
            borderRadius: 4,
            padding: 6,
            font: {
              weight: 'bold',
              size: 13
            },
            formatter: (value: number) => {
              // Formata como na tabela: 5.800.000 → 5.800.000 ou 5,8M
              if (value >= 1000000) {
                return (value / 1000000).toFixed(1) + 'M';
              }
              return new Intl.NumberFormat('pt-BR', {
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
              }).format(value);
            }
          }
        }
      },
      plugins: [ChartDataLabels]   // importante: ativa o plugin
    });
  }

  ngOnDestroy() {
    if (this.chart) {
      this.chart.destroy();
    }
  }
}