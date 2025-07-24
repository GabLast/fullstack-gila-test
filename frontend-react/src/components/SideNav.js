import { NavLink } from 'react-router';
import { HOME,  MESSAGE_FORM, NOTIFICATION_TAB } from '../config/routes';

const navItems = [
  { name: 'Home', path: HOME },
  { name: 'Message Form', path: MESSAGE_FORM },
  { name: 'Notification Logs', path: NOTIFICATION_TAB },
];

function SideNav() {
  return (
    <nav className="h-screen w-64 bg-gray-800 text-white flex flex-col py-8">
      <div className="mb-8 px-6 text-2xl font-bold">Notification Client</div>
      <ul className="flex flex-col gap-2 px-4">
        {navItems.map(item => (
          <li key={item.path}>
            <NavLink
              to={item.path}
              className={({ isActive }) =>
                `block py-2 px-4 rounded hover:bg-gray-700 transition ${
                  isActive ? 'bg-gray-700 font-semibold' : ''
                }`
              }
            >
              {item.name}
            </NavLink>
          </li>
        ))}
      </ul>
    </nav>
  );
}

export default SideNav;