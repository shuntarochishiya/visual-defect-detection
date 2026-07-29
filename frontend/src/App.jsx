import { useEffect, useState } from 'react'
import './App.css'

const API = 'http://localhost:8080/api/v1'

export default function App() {
  const [summary, setSummary] = useState({ totalReports: 0, anomalyReports: 0, averageLatencyMs: 0 })
  const [reports, setReports] = useState([])
  const [online, setOnline] = useState(false)
  const visual = reports.find((r) => r.modality === 'VISUAL')
  const imageUrl = visual?.fileName ? `http://localhost:8000/images/${encodeURIComponent(visual.fileName)}` : '/sample-defect.jpg'

  async function refresh() {
    try {
      const [s, r] = await Promise.all([fetch(`${API}/dashboard/summary`), fetch(`${API}/reports`)]);
      if (!s.ok || !r.ok) throw new Error('offline')
      setSummary(await s.json()); setReports((await r.json()).items || []); setOnline(true)
    } catch { setOnline(false) }
  }
  useEffect(() => { refresh(); const id = setInterval(refresh, 3000); return () => clearInterval(id) }, [])

  return <main className="shell">
    <header className="topbar"><div className="brand"><span className="brand-mark">+</span><div><strong>VISUAL DEFECT</strong><small>DETECTION PLATFORM</small></div></div><div className="live"><span className={online ? 'dot' : 'dot off'} />{online ? 'SYSTEM ONLINE' : 'CONNECTING'}<button onClick={refresh}>Refresh</button></div></header>
    <section className="intro"><div><p className="eyebrow">OPERATIONS OVERVIEW / 2026-07-29</p><h1>Inspection command center</h1><p className="muted">Real-time visual and timing anomaly monitoring.</p></div><div className="pulse"><span /> Live monitoring<br /><b>Auto refresh / 3 sec</b></div></section>
    <section className="cards"><Metric label="TOTAL REPORTS" value={summary.totalReports} tone="blue" icon="#" /><Metric label="ANOMALIES DETECTED" value={summary.anomalyReports} tone="red" icon="!" /><Metric label="AVG. LATENCY" value={`${Math.round(summary.averageLatencyMs)} ms`} tone="green" icon="^" /><Metric label="ACTIVE SERVICES" value="3 / 3" tone="violet" icon="~" /></section>
    <section className="grid"><div className="panel services"><div className="panel-head"><h2>Service health</h2><span className="tag">LIVE</span></div><Service name="Platform service" detail="API gateway / :8080" /><Service name="Vision detector" detail="YOLO inference / :8001" /><Service name="Timing detector" detail="TimeMixer analysis / :8002" /></div><div className="panel focus"><div className="panel-head"><h2>Detection mix</h2><span className="muted">Current session</span></div><div className="mix"><div className="ring"><b>{summary.totalReports ? Math.round(summary.anomalyReports / summary.totalReports * 100) : 0}%</b><span>anomaly rate</span></div><div className="legend"><p><i className="blue" />Visual inspections <b>{reports.filter(r => r.modality === 'VISUAL').length}</b></p><p><i className="violet" />Time-series inspections <b>{reports.filter(r => r.modality === 'TIME_SERIES').length}</b></p></div></div></div></section>
    <section className="panel visual-panel"><div className="panel-head"><div><h2>Visual inspection preview</h2><span className="muted">Latest image with detected region</span></div><span className="tag">{visual ? 'DETECTED' : 'WAITING'}</span></div><div className="visual-content"><div className="image-stage"><img src={imageUrl} alt="NEU-DET sample" /><span className="detection-box"><b>{visual?.anomalyType || 'Sample defect'}</b><small>{visual ? `${Math.round((visual.anomalyScore || 0) * 100)}% confidence` : 'Awaiting result'}</small></span></div><div className="visual-meta"><p><span>FILE</span>{visual?.fileName || "-"}</p><p><span>MODEL</span>{visual?.modelName || 'mock-yolo'} / {visual?.modelVersion || 'v1'}</p><p><span>SEVERITY</span><strong className="danger-text">{visual?.severity || 'MEDIUM'}</strong></p><p><span>STATUS</span><strong className="success-text">{visual ? 'ANOMALY DETECTED' : 'NO RESULT'}</strong></p></div></div></section>
    <section className="panel table-panel"><div className="panel-head"><div><h2>Latest inspection reports</h2><span className="muted">Most recent events from the detection pipeline</span></div><span className="tag">{reports.length} RECORDS</span></div><div className="table-wrap"><table><thead><tr><th>REPORT ID</th><th>STREAM</th><th>MODALITY</th><th>RESULT</th><th>SEVERITY</th><th>MODEL</th></tr></thead><tbody>{reports.slice(0, 12).map(r => <tr key={r.reportId}><td className="mono">{r.reportId?.slice(0, 8)}...</td><td>{r.streamId}</td><td><span className={`pill ${r.modality === 'VISUAL' ? 'visual' : 'timing'}`}>{r.modality}</span></td><td><span className={r.anomaly ? 'result danger' : 'result'}>{r.anomaly ? 'Anomaly' : 'Normal'}</span><small>{r.anomalyType}</small></td><td><span className={`severity ${(r.severity || '').toLowerCase()}`}>{r.severity || '-'}</span></td><td className="model">{r.modelName || '-'} <small>{r.modelVersion}</small></td></tr>)}</tbody></table></div></section>
    <footer>PLATFORM SERVICE / IN-MEMORY DEMO MODE</footer>
  </main>
}
function Metric({ label, value, tone, icon }) { return <div className={`metric ${tone}`}><span className="metric-icon">{icon}</span><div><p>{label}</p><strong>{value}</strong></div></div> }
function Service({ name, detail }) { return <div className="service"><span className="service-dot" /><div><b>{name}</b><small>{detail}</small></div><span className="up">UP</span></div> }

