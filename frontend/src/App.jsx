import { useEffect, useState } from 'react'
import './App.css'

const API = 'http://localhost:8080/api/v1'

function App() {
  const [summary, setSummary] = useState({ totalReports: 0, anomalyReports: 0, averageLatencyMs: 0 })
  const [reports, setReports] = useState([])
  const [online, setOnline] = useState(false)

  const refresh = async () => {
    try {
      const [summaryRes, reportsRes] = await Promise.all([
        fetch(`${API}/dashboard/summary`),
        fetch(`${API}/reports`),
      ])
      if (!summaryRes.ok || !reportsRes.ok) throw new Error('API unavailable')
      setSummary(await summaryRes.json())
      const reportData = await reportsRes.json()
      setReports(reportData.items || [])
      setOnline(true)
    } catch {
      setOnline(false)
    }
  }

  useEffect(() => { refresh(); const timer = setInterval(refresh, 3000); return () => clearInterval(timer) }, [])

  return (
    <main className="shell">
      <header className="topbar">
        <div className="brand"><span className="brand-mark">◈</span><div><strong>VISUAL DEFECT</strong><small>DETECTION PLATFORM</small></div></div>
        <div className="live"><span className={online ? 'dot' : 'dot off'} />{online ? 'SYSTEM ONLINE' : 'CONNECTING'}<button onClick={refresh}>Refresh</button></div>
      </header>
      <section className="intro"><div><p className="eyebrow">OPERATIONS OVERVIEW / 2026-07-29</p><h1>Inspection command center</h1><p className="muted">Real-time visual and timing anomaly monitoring.</p></div><div className="pulse"><span /> Live monitoring<br /><b>Auto refresh · 3 sec</b></div></section>
      <section className="cards">
        <Metric label="TOTAL REPORTS" value={summary.totalReports} tone="blue" icon="◫" />
        <Metric label="ANOMALIES DETECTED" value={summary.anomalyReports} tone="red" icon="!" />
        <Metric label="AVG. LATENCY" value={`${Math.round(summary.averageLatencyMs)} ms`} tone="green" icon="↗" />
        <Metric label="ACTIVE SERVICES" value="3 / 3" tone="violet" icon="⌁" />
      </section>
      <section className="grid"><div className="panel services"><div className="panel-head"><h2>Service health</h2><span className="tag">LIVE</span></div><Service name="Platform service" detail="API gateway · :8080" /><Service name="Vision detector" detail="YOLO inference · :8001" /><Service name="Timing detector" detail="TimeMixer analysis · :8002" /></div>
        <div className="panel focus"><div className="panel-head"><h2>Detection mix</h2><span className="muted">Current session</span></div><div className="mix"><div className="ring"><b>{summary.totalReports ? Math.round(summary.anomalyReports / summary.totalReports * 100) : 0}%</b><span>anomaly rate</span></div><div className="legend"><p><i className="blue" />Visual inspections <b>{reports.filter(r => r.modality === 'VISUAL').length}</b></p><p><i className="violet" />Time-series inspections <b>{reports.filter(r => r.modality === 'TIME_SERIES').length}</b></p></div></div></div></section>
      <section className="panel table-panel"><div className="panel-head"><div><h2>Latest inspection reports</h2><span className="muted">Most recent events from the detection pipeline</span></div><span className="tag">{reports.length} RECORDS</span></div><div className="table-wrap"><table><thead><tr><th>REPORT ID</th><th>STREAM</th><th>MODALITY</th><th>RESULT</th><th>SEVERITY</th><th>MODEL</th></tr></thead><tbody>{reports.slice(0, 12).map((r) => <tr key={r.reportId}><td className="mono">{r.reportId?.slice(0, 8)}…</td><td>{r.streamId}</td><td><span className={`pill ${r.modality === 'VISUAL' ? 'visual' : 'timing'}`}>{r.modality === 'VISUAL' ? 'VISUAL' : 'TIME SERIES'}</span></td><td><span className={r.anomaly ? 'result danger' : 'result'}>{r.anomaly ? '● Anomaly' : '● Normal'}</span>{r.anomalyType && <small>{r.anomalyType}</small>}</td><td><span className={`severity ${(r.severity || '').toLowerCase()}`}>{r.severity || '—'}</span></td><td className="model">{r.modelName || '—'} <small>{r.modelVersion}</small></td></tr>)}</tbody></table>{!reports.length && <div className="empty">No reports yet. Start the simulator to see live results.</div>}</div></section>
      <footer>PLATFORM SERVICE <span>·</span> IN-MEMORY DEMO MODE <span>·</span> LAST SYNC {new Date().toLocaleTimeString()}</footer>
    </main>
  )
}

function Metric({ label, value, tone, icon }) { return <div className={`metric ${tone}`}><span className="metric-icon">{icon}</span><div><p>{label}</p><strong>{value}</strong></div><span className="trend">↗</span></div> }
function Service({ name, detail }) { return <div className="service"><span className="service-dot" /><div><b>{name}</b><small>{detail}</small></div><span className="up">UP</span></div> }
export default App
