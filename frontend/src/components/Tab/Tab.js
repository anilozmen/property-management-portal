import { useState } from "react";
import { TabHeaderEntry } from "./TabHeaderEntry";
import "./Tab.css";

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

export default Tab;
