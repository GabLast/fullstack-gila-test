import { NavLink } from "react-router";
import { HOME, MESSAGE_FORM, NOTIFICATION_TAB } from '../config/routes';

const navItems = [
  { name: 'Home', path: HOME },
  { name: 'Message Form', path: MESSAGE_FORM },
  { name: 'Notification Logs', path: NOTIFICATION_TAB },
];

function SideNav() {
  return (
    // <div className="container size-full bg-blue-200 p-1 m-4">
    <div className="flex bg-blue-200 grow p-1">
      <div className="flex h-16 items-center sm:ml-2">
        <div className="flex space-x-4">
          {navItems.map(item => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `sidenav-link${isActive ? ' sidenav-link-active' : ''}`
              }
            >
              {item.name}
            </NavLink>
          ))}
        </div>
      </div>
    </div>
  );
}

export default SideNav;