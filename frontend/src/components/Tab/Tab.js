import { useState } from "react";

const Tab = ({ tabDetails }) => {
  const [activeTab, setActiveTab] = useState(0);
  if (tabDetails.length === 0) return;

  return (
    <div className="col-md-10 offset-md-1">
      <ul className="nav nav-pills-a nav-pills mb-3 section-t3">
        {tabDetails.map((c, index) => (
          <TabHeaderEntry
            key={index}
            title={c.title}
            isActive={activeTab === index}
            onClick={() => setActiveTab(index)}
          />
        ))}
      </ul>
      <div>
        {tabDetails.map((c, index) => (
          <div key={index}>{index === activeTab && c.content}</div>
        ))}
      </div>
    </div>
  );
};

function TabHeaderEntry({ onClick, title, isActive }) {
  let className = "nav-link";
  if (isActive) className += " active";

  return (
    <li className="nav-item" onClick={onClick}>
      <span className={className}>{title}</span>
    </li>
  );
}

export default Tab;
