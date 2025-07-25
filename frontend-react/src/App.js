// import logo from './logo.svg';
import './App.css';
import './output.css';
import SideNav from './components/SideNav';
import { BrowserRouter, Routes, Route } from 'react-router';
import { MESSAGE_FORM, NOTIFICATION_TAB } from './config/routes';
import FormMessage from './forms/FormMessage';
import { TabNotification } from './tabs/TabNotification';

function App() {
  return (
    <BrowserRouter>
      <SideNav />
      <div className="flex p-1">
        <Routes>
          <Route path="/" element={<span>Home Page</span>} />
          <Route path={MESSAGE_FORM} element={<FormMessage />} />
          <Route path={NOTIFICATION_TAB} element={<TabNotification />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
